# LUDITO-test

## Описание проекта

Данный проект представляет собой REST API для финансового продукта, реализованного на базе Java Spring Boot. В системе
предусмотрены пользователи, у каждого из которых есть баланс. Баланс пользователя изменяется посредством проведения
транзакций.

### Основной функционал:

- Создание и хранение пользователей с балансом.
- Учет транзакций, влияющих на баланс пользователей.
- Поддержка создания, редактирования и получения пользователей через REST API.
- Поддержка создания транзакций через REST API.
- Получение списка транзакций с возможностью фильтрации по пользователю и интервалу дат.

## Требования к реализации

1. **Таблицы в базе данных PostgreSQL:**
    - Таблица пользователей (`users`) с необходимыми полями (например, id, имя, email, баланс).
    - Таблица транзакций (`transactions`) с необходимыми полями (например, id, user_id, сумма, тип операции, дата).
    - Таблицы заполнены тестовыми (моковыми) данными.

2. **REST API:**
    - Методы для пользователей:
        - Создание пользователя.
        - Редактирование пользователя.
        - Получение информации о пользователях.
    - Методы для транзакций:
        - Создание транзакции.
        - Получение списка транзакций:
            - Все транзакции.
            - По конкретному пользователю.
            - По интервалу дат.

3. **Используемые технологии:**
    - Java 21 и Spring Boot.
    - Spring Data JPA для работы с базой данных.
    - PostgreSQL как СУБД.

4. **Рекомендуемые дополнения (необязательно):**
    - Валидация данных и обработка ошибок.
    - Реализация безопасности (например, JWT аутентификация).

## Инструкция по запуску проекта

### Предварительные требования:

- Установлен Docker и Docker Compose.
- Порт 5433 свободен для PostgreSQL.

### Шаги запуска:

1. Клонируйте репозиторий:
   ```bash
   git clone https://github.com/dev-abdulhay/LUDITO-test.git
   cd LUDITO-test
   ```

2. Постройте и запустите контейнер с приложением:
   ```bash
   docker compose up --build
   ```

3. Приложение будет доступно по адресу:
   ```
   http://localhost:8081
   ```

## API документация

Проект включает интерактивную документацию API через Swagger UI, доступную по адресу:

   ```
   http://localhost:8081/swagger-ui/index.html
   ```

## Тестирование REST API

- Для тестирования API рекомендуется использовать такие инструменты, как Postman или curl.
- Базовые эндпоинты:

## Описание контроллеров REST API

В проекте реализованы следующие основные контроллеры с набором соответствующих REST методов для работы с пользователями,
транзакциями и финансовыми операциями:

### 1. AuthController

Отвечает за регистрацию, аутентификацию и управление сессиями пользователей.

- `POST /api/v1/auth/register` — регистрация нового пользователя.
- `POST /api/v1/auth/login` — инициализация процесса входа (логина).
- `POST /api/v1/auth/login/verify` — верификация логина (например, по коду).
- `POST /api/v1/auth/logout` — выход из системы (логаут).

### 2. UserController

Работает с информацией пользователей.

- `GET /api/v1/user` — получить информацию о текущем пользователе (авторизованном).
- `POST /api/v1/user/update` — обновить информацию пользователя.
- `POST /api/v1/user/password/change` — смена пароля пользователя.

### 3. WalletController

Обрабатывает операции с "кошельками" пользователей, где хранится баланс в различных валютах.

- `GET /api/v1/wallet` — получить список всех кошельков текущего пользователя.
- `POST /api/v1/wallet/one` — получить информацию об одном кошельке по UUID.
- `POST /api/v1/wallet/add` — добавить новый кошелек.

### 4. TransferController

Отвечает за выполнение инициализации, расчёта комиссии и непосредственного проведения переводов (транзакций) между
кошельками пользователей.

- `POST /api/v1/transfer/fee` — рассчитать комиссию перевода.
- `POST /api/v1/transfer/init` — инициализировать перевод.
- `POST /api/v1/transfer/execute` — выполнить перевод средств.

### 5. HistoryController

Предназначен для поиска и получения истории транзакций (операций) с поддержкой фильтров.

- `POST /api/v1/history/search` — поиск транзакций с возможностью фильтрации по пользователю, дате и другим параметрам.

---

Все контроллеры используют аннотации для валидации входящих данных (`@Valid`) и возвращают обёрнутые в `ResponseEntity`
стандартизированные ответы.

Для базового API префикс `/api/v1/` определяется константами и применяется на уровне роутинга.  
Эндпоинты разбиты по модулям: авторизация, пользователи, кошельки, переводы и история транзакций.

Этот подход обеспечивает ясное разделение ответственности и удобство поддержки и расширения REST API финансового
продукта.

---

# Authentication Process Guide

This section explains how to authenticate users using the two-step login verification implemented in this project’s API.

## Step 1: Initiate Login

Send a `POST` request to the login endpoint with the user's credentials:

```bash
curl -X POST \
'http://localhost:8081/web/admin/api/v1/auth/login' \
-H 'accept: */*' \
-H 'Content-Type: application/json' \
-d '{
"username": "user1",
"password": "password1"
}'
```

### Response

On successful request, the server responds with a JSON containing:

- `identity`: A unique identifier for the login session.
- `message`: A confirmation message indicating the login initialization and includes an OTP (One Time Password) code
  which the user must verify.

Example:

```json
{
  "identity": "5d154e58-c500-4c9f-bdb5-65eb1e0da34b",
  "message": "Login initialized successfully. Please verify your OTP code. (88534)"
}
```

## Step 2: Verify OTP Code

To complete the login, send a `POST` request to the verification endpoint with the received `identity` and the OTP code:

```bash
curl -X POST \
'http://localhost:8081/web/admin/api/v1/auth/login/verify' \
-H 'accept: */*' \
-H 'Content-Type: application/json' \
-d '{
"identity": "5d154e58-c500-4c9f-bdb5-65eb1e0da34b",
"code": "88534"
}'
```

### Response

If verification is successful, the server returns:

- `accessToken`: JWT access token to authenticate subsequent requests.
- `refreshToken`: JWT refresh token to obtain new access tokens.
- `accessTokenExpire`: Access token expiration timestamp.
- `refreshTokenExpire`: Refresh token expiration timestamp.

Example:

```json
{
  "accessToken": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyMSIsImV4cCI6MTc1MzIwMTAzMSwidXNlcklkIjoiMDg3N2E1MmMtZjRhZS00MmM3LTg1NzItN2ExZjUyOWYyNjMxIn0.YPMIMkm2JdN3xn33n1exWKlWJ9df2KeyUxjIV0Y1Wus",
  "refreshToken": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyMSIsImV4cCI6MTc1NTcyNTgzMX0.4ztasTDg8q_zVSW3PZQLID_vkSXo_TjAK7IR4IDImh8",
  "accessTokenExpire": "2025-07-22T21:17:11",
  "refreshTokenExpire": "2025-08-21T02:37:11"
}
```

## Using the Access Token

Include the `accessToken` in the `X-Auth-Token` header (as a ApiKey) for all authenticated API requests, for example:

```

X-Auth-Token: eyJhbGciOiJ...
```

This token authenticates the user and grants access to protected endpoints.

---

This two-step authentication ensures secure login with OTP verification, providing robust access control for the API.

# Transfer Process Guide

This section describes the three-step process to perform a transfer using the API.

---

## Step 1: Calculate Transfer Fee

Send a `POST` request to the `/transfer/fee` endpoint to calculate the commission and amounts involved in the transfer.

### Request

```bash
curl -X POST \
'http://localhost:8081/web/admin/api/v1/transfer/fee' \
-H 'accept: */*' \
-H 'X-Auth-Token: <accessToken>' \
-H 'Content-Type: application/json' \
-d '{
"debitAccountId": "d3823827-da96-4540-b874-0999ea4d5c76",
"creditAccountId": "71808f5e-925d-47f6-83e0-ee11ac6652f4",
"creditAccount": "",
"operationType": "OWN2OTHER",
"amount": 500000,
"description": "First Transfer"
}'
```

### Response

The server returns a summary of the transfer amounts including the commission fee:

```json
{
  "commissionAmount": 5000,
  "creditAmount": 500000,
  "debitAmount": 505000
}
```

- `commissionAmount`: The fee charged for this transfer.
- `creditAmount`: The amount that the recipient will receive.
- `debitAmount`: The total amount that will be deducted from the sender's account (transfer amount + commission).

---

## Step 2: Initialize Transfer

Send a `POST` request to the `/transfer/init` endpoint with the same transfer details to create and initialize the
transfer.

### Request

```bash
curl -X POST \
'http://localhost:8081/web/admin/api/v1/transfer/init' \
-H 'accept: */*' \
-H 'X-Auth-Token: <accessToken>' \
-H 'Content-Type: application/json' \
-d '{
"debitAccountId": "d3823827-da96-4540-b874-0999ea4d5c76",
"creditAccountId": "71808f5e-925d-47f6-83e0-ee11ac6652f4",
"creditAccount": "",
"operationType": "OWN2OTHER",
"amount": 500000,
"description": "First Transfer"
}'
```

### Response

A unique `transferId` is returned, representing the initialized transfer:

```json
{
  "transferId": "e3575287-a990-42ee-9827-f2bffef90759"
}
```

---

## Step 3: Execute Transfer

Finally, send a `POST` request to `/transfer/execute` with the `transferId` to perform the actual transfer transaction.

### Request

```bash
curl -X POST \
'http://localhost:8081/web/admin/api/v1/transfer/execute' \
-H 'accept: */*' \
-H 'X-Auth-Token: <accessToken>' \
-H 'Content-Type: application/json' \
-d '{
"transferId": "e3575287-a990-42ee-9827-f2bffef90759"
}'
```

### Response

Confirms that the transfer was successfully executed:

```json
{
  "message": "Transfer executed successfully"
}
```

---

## Notes

- Always include a valid `X-Auth-Token` (the access token from login verification) in request headers for
  authentication.
- Use UUIDs exactly as returned by previous steps to maintain the integrity of requests.
- This multi-step process helps ensure the transfer is validated, correctly charged, and processed securely.



---



# Transaction History Search Guide

This section explains how to search for transaction history using the API in two common scenarios.

---

## Step 1: Search Transaction History by Type

Send a `POST` request to the `/history/search` endpoint with the transaction type and paging parameters to retrieve a list of matching operations.


### Request

```bash
curl -X POST \
'http://localhost:8081/web/admin/api/v1/history/search' \
-H 'accept: */*' \
-H 'X-Auth-Token: <accessToken>' \
-H 'Content-Type: application/json' \
-d '{
"type": "OWN2OTHER",
"paging": {
"page": 0,
"size": 10
}
}'
```

### Response

The response includes an array of operations matching the filter, each with details such as UUID, status, amounts, and
description. It also includes paging info.

```json
{
"operations": [
{
"uuid": "0f4de4df-d1bb-42e9-8bfc-8694bebd6406",
"order": 0,
"status": "INIT",
"type": "OWN2OTHER",
"creditWalletUuid": "71808f5e-925d-47f6-83e0-ee11ac6652f4",
"debitWalletUuid": "d3823827-da96-4540-b874-0999ea4d5c76",
"creditAmount": 500000,
"debitAmount": 505000,
"commissionAmount": 5000,
"userUuid": "d60bce90-ee97-49c4-aee3-ac2ac2c1d8f2",
"executeDate": null,
"description": "First Transfer"
},
{
"uuid": "e3575287-a990-42ee-9827-f2bffef90759",
"order": 0,
"status": "SUCCESS",
"type": "OWN2OTHER",
"creditWalletUuid": "71808f5e-925d-47f6-83e0-ee11ac6652f4",
"debitWalletUuid": "d3823827-da96-4540-b874-0999ea4d5c76",
"creditAmount": 500000,
"debitAmount": 505000,
"commissionAmount": 5000,
"userUuid": "d60bce90-ee97-49c4-aee3-ac2ac2c1d8f2",
"executeDate": "2025-07-22 03:19:02",
"description": "First Transfer"
}
],
"paging": {
"page": 0,
"size": 10
}
}
```

- The `status` field indicates the current state of the transfer (e.g., `INIT`, `SUCCESS`).
- `executeDate` is null if the transfer has not been completed.

---

## Step 2: Search Transaction History by Type and Date Range

You can filter transaction history not only by type but also by a specific date range, specifying `fromDate` and
`toDate`.

### Request

```bash
curl -X POST \
'http://localhost:8081/web/admin/api/v1/history/search' \
-H 'accept: */*' \
-H 'X-Auth-Token: <accessToken>' \
-H 'Content-Type: application/json' \
-d '{
"type": "OWN2OTHER",
"fromDate": "2025-07-21 17:39:16",
"toDate": "2025-07-23 17:39:16",
"paging": {
"page": 0,
"size": 10
}
}'
```

### Response

The server returns only transactions matching both the type and date range filters.

```json
{
  "operations": [
    {
      "uuid": "e3575287-a990-42ee-9827-f2bffef90759",
      "order": 0,
      "status": "SUCCESS",
      "type": "OWN2OTHER",
      "creditWalletUuid": "71808f5e-925d-47f6-83e0-ee11ac6652f4",
      "debitWalletUuid": "d3823827-da96-4540-b874-0999ea4d5c76",
      "creditAmount": 500000,
      "debitAmount": 505000,
      "commissionAmount": 5000,
      "userUuid": "d60bce90-ee97-49c4-aee3-ac2ac2c1d8f2",
      "executeDate": "2025-07-22 03:19:02",
      "description": "First Transfer"
    }
  ],
  "paging": {
    "page": 0,
    "size": 10
  }
}
```

---

## Important Notes

- Include a valid `X-Auth-Token` in each request header for authentication.
- `paging` object controls pagination with `page` number (starting at 0) and `size` per page.
- Date fields must be formatted as `"yyyy-MM-dd HH:mm:ss"`.

```





Спасибо за внимание! В случае вопросов обращайтесь к документации проекта или внутрь реализации.
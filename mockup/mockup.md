# Сценарии использования (Use Cases)

## 1. Сценарий: Регистрация нового пользователя

### Описание:
Пользователь регистрируется в системе, чтобы получить доступ к функционалу.

### Основной поток:
1. Пользователь открывает страницу регистрации.
2. Система отображает форму регистрации.
3. Пользователь вводит свои данные (логин, email, пароль).
4. Пользователь нажимает кнопку "Зарегистрироваться".
5. Выполняется переход на страницу с сообщением о том, что регистрация прошла успешно.

### Альтернативный поток:
- Если логин занят или почта уже зарегестрирована, то после нажатия кнопки переход на страницу с сообщением о том, что регистрация прошла успешно, не происходит. Появляется сообщение о том, что пароль занят. 
---

## 2. Сценарий: Вход в систему

### Описание:
Пользователь входит в систему, используя свои учетные данные.

### Основной поток:
1. Пользователь открывает страницу входа.
2. Система отображает форму входа.
3. Пользователь вводит логин и пароль.
4. Пользователь нажимает кнопку "Войти".
5. Система проверяет учетные данные.
6. Система предоставляет доступ к личному кабинету и переводит на главную страницу.

### Альтернативный поток:
- Если данные неверны, система отображает сообщение об ошибке. Перехода на главную страницу не происходит.

---

## 3. Сценарий: Просмотр видео с комментированием и реакцией.

### Описание:
Пользователь включает видео, ставит лайк/дизлайк и оставляет комментарий. Предусловие: пользователь авторизован и находится на главной странице.

### Основной поток:
1. Пользователь открывает одно из видео на экране.

2. Система переводит на страницу с видео и отображает его на экране.

3. Во время просмотра пользователь видит список комментариев под видео.

4. Пользователь нажимает на поле "Написать комментарий".

5. Пользователь вводит текст комментария и нажимает кнопку "Отправить".

6. Система добавляет комментарий пользователя в список и обновляет страницу.

7. Пользователь нажимает на кнопку "Лайк" или "Дизлайк" под видео, чтобы выразить свою реакцию.

8. Система обновляет счетчик реакций и сохраняет выбор пользователя.
---

## 4. Сценарий: Загрузка видео на аккаунт

### Описание:
Пользователь загружает видео на свой аккаунт для публикации на платформе.Предусловие: пользователь авторизован.

### Основной поток:
1. Пользователь входит в свой канал.
2. Пользователь нажимает кнопку "Загрузить видео".
3. Система открывает страницу загрузки видео.
4. Пользователь выбирает видеофайл(ы) на своем устройстве.
5. Пользователь заполняет название.
6. Пользователь нажимает кнопку "Загрузить".
7. Система загружает видео.
8. Система публикует видео на аккаунте пользователя.
---

## 5. Сценарий: Администратор скрывает видео

### Описание:
Администратор скрывает видео, нарушающее правила платформы. Предусловие: администратор авторизован.

### Основной поток:
1. Администратор переходит на страницу с видео для скрытия.
2. Администратор нажимает кнопку "Скрыть видео".
3. Система скрывает видео от публичного доступа. Администратора переводит на страницу с сообщением об успешном выполнении операции.

---

## 6. Сценарий: Администратор блокирует пользователя

### Описание:
Администратор блокирует пользователя. Предусловие: администратор авторизован.

### Основной поток:
1. Администратор переходит на страницу блокировки пользователей.
2. Администратор вводит логин блокируемого пользователя.
3. Администратор нажимает кнопку "Заблокировать пользователя".
4. Система блокирует аккаунт пользователя. Администратора переводит на страницу с сообщением об успешном выполнении операции.

### Альтернативные потоки:
Если такого пользователя нет, то система сообщает об этом администратора. Перехода на страницу с сообщением об успехе не происходит.

---

## 7. Сценарий: Администратор дает права администратора

### Описание:
Администратор назначает другому пользователю права администратора. Предусловие: администратор авторизован.

### Основной поток:
1. Администратор переходит на страницу выдачи прав администратора.
2. Администратор вводит логин пользователя, которому выдаются права.
4. Администратор нажимает кнопку "Выдать права".
5. Система предоставляет пользователю права администратора. Администратора переводит на страницу с сообщением об успешном выполнении операции.

### Альтернативные потоки:
Если такого пользователя нет, то система сообщает об этом администратора. Перехода на страницу с сообщением об успехе не происходит
---

## 8. Сценарий: Скачивание видео

### Описание:
Пользователь скачивает видео с платформы на свое устройство.

### Основной поток:
1. Пользователь открывает страницу с видео.
2. Пользователь нажимает кнопку "Скачать".
3. Система начинает загрузку видео на устройство пользователя.
4. Пользователь получает видео в указанной папке.

## Замечание
Администратор имеет те же возможности, что и обычный пользователь, плюс возможности блокировки пользователей, назначения администраторов и скрытия видео. Чтобы получить доступ к видео, нужно авторизоваться, т.е. без авторизации видео не видны.

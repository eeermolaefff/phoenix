FROM python:3.9.6

# Установить рабочую директорию для контейнера
WORKDIR /app

# Копируем requirements.txt в контейнер
# COPY ./requirements.txt /app/requirements.txt

# Обновляем pip и устанавливаем зависимости
RUN pip install --upgrade pip
# RUN pip install -r /app/requirements.txt

# Копируем все файлы проекта в контейнер
COPY ./app/* /app/

# Указываем команду для запуска приложения
CMD ["python", "main.py"]
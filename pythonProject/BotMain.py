import asyncio
import logging
import requests
import random
from aiogram import Bot, Dispatcher, types
from aiogram.types import reply_keyboard_markup, keyboard_button,reply_keyboard_remove
from aiogram.filters import CommandStart, Command
from bs4 import BeautifulSoup as bsoup
# Идентификатор для бота и ссылка на сайт с анекдотами
TOKEN_API = "6842064304:AAFvCfPNL1SH9UVvLOZzoeHfoXbHXa1yBH0"
URL = 'https://www.anekdot.ru/last/good/'
# Передача токена боту и добавление диспетчера для дальнейшей обработки команд
bot = Bot(token=TOKEN_API)
dp = Dispatcher()
# Список текстовых переменных для вывода командами
HELP_COMMAND = """
<b>/help</b> - список команд
<b>/description</b> - описание бота
<b>/joke</b> - случайный анекдот с сайта <b><a href="https://www.anekdot.ru/last/good/">anekdot.ru</a></b>
Также бот может присылать подборку из 10 товаров с сайта <b><a href="https://n-katalog.ru/">n-katalog.ru</a></b> по заданному вами ключевому слову."""
DESC_COMMAND = """
<b>Cloudy</b> - <b>бот</b>, разработанный в рамках курсовой работы студентом М.С. Христюк."""
# Функция-парсер для анекдотов
def parser(url):
    r = requests.get(url)
    soup = bsoup(r.text, "html.parser")
    anekdots = soup.find_all('div', class_='text')
    return [c.text for c in anekdots]
# Передача списка анекдотов и перемешивание их
jokes_list = parser(URL)
random.shuffle(jokes_list)
# Функция реагирования на команду start
@dp.message(CommandStart())
async def handle_start(message: types.Message):
    kb = [
        [
            types.KeyboardButton(text="/help"),
            types.KeyboardButton(text="/description"),
            types.KeyboardButton(text="/joke"),
        ],
        ]
    keyboard = types.ReplyKeyboardMarkup(
        keyboard=kb,
        resize_keyboard=True,
        input_field_placeholder="Для вывода списка команд введите /help."
    )
    await message.answer(text=f"Здравствуйте, {message.from_user.full_name}!\nДля вывода списка команд введите <b>/help</b>.",
                         parse_mode="HTML",
                         reply_markup=keyboard)
# Функция реагирования на команду help
@dp.message(Command("help"))
async def help_cmd(message: types.Message):
    await bot.send_message(chat_id=message.from_user.id,
                           text=HELP_COMMAND,
                           parse_mode="HTML",
                           disable_web_page_preview=1)
# Функция реагирования на команду description
@dp.message(Command("description"))
async def desc_cmd(message: types.Message):
    await bot.send_message(chat_id=message.from_user.id,
                           text=DESC_COMMAND,
                           parse_mode="HTML")
# Функция реагирования на команду joke
@dp.message(Command("joke"))
async def joke_cmd(message: types.Message):
    await bot.send_message(chat_id=message.from_user.id,
                           text=jokes_list[0])
    del jokes_list[0]
# Функция-парсер для нахождения товаров по ключевому слову
@dp.message()
async def parser(message: types.message):
    url = "https://n-katalog.ru/search?keyword=" + message.text
    request = requests.get(url)
    soup = bsoup(request.text, "html.parser")
    all_links = soup.find_all('a', class_='model-short-title')
    for link in all_links:
        url = "https://n-katalog.ru" + link["href"]
        request = requests.get(url)
        soup = bsoup(request.text, "html.parser")
        name = soup.find("div", class_="page-title").text
        img = soup.find("div", class_="img200")
        img = img.findChildren("img")[0]
        img = "https://n-katalog.ru/" + img["src"]
        await bot.send_photo(message.chat.id, img,
        caption="<b>" + name + "</b>\n<i>" + f"</i>\n<a href='{url}'>Ссылка на сайт</a>",
        parse_mode="html")
        if all_links.index(link) == 9:
            break
# Постоянный опрос обновлений бота + поверхностное логирование
async def main():
    logging.basicConfig(level=logging.INFO)
    await dp.start_polling(bot)
# Точка входа
if __name__ == '__main__':
    asyncio.run(main())

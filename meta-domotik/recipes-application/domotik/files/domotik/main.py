from aiohttp import web

from domotik.other import get_text


async def hello_handler(request):
    return web.Response(text=get_text())


def main():
    app = web.Application()
    app.add_routes([web.get('/', hello_handler)])
    web.run_app(app)


if __name__ == "__main__":
    main()

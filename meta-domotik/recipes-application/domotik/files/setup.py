from setuptools import setup, find_packages

setup(
    name="Domotik",
    description="Domotic application",
    version="0.1.0",
    url="",
    author="Franck Barbenoire",
    author_email="contact@franck-barbenoire.fr",
    packages=["domotik"],
    package_dir={"domotik": "domotik"},
    # packages=find_packages(include=(["domotik", "domotik.*"])),
    entry_points={
        "console_scripts": ["domotik=domotik.main:main"]
    },
    python_requires='>=3.10',
    install_requires=["aiohttp >= 3.8.4"],
)

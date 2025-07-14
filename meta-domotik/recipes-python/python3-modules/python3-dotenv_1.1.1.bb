SUMMARY = "ython-dotenv reads key-value pairs from a .env file and can set them as environment variables"
HOMEPAGE = "https://github.com/theskumar/python-dotenv"
AUTHOR = "Saurabh Kumar <thes.kumar+web@gmail.com>", "Bertrand Bonnefoy-Claudet <bertrand@bertrandbc.com>"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=e914cdb773ae44a732b392532d88f072"

SRC_URI[sha256sum] = "a8a6399716257f45be6a007360200409fce5cda2661e3dec71d23dc15f6189ab"

PYPI_PACKAGE = "python_dotenv"
inherit pypi setuptools3

S = "${WORKDIR}/python_dotenv-${PV}"

# RDEPENDS:${PN} = "\
# "

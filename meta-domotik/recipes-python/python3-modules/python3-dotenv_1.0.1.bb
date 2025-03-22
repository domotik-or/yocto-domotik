SUMMARY = "ython-dotenv reads key-value pairs from a .env file and can set them as environment variables"
HOMEPAGE = "https://github.com/theskumar/python-dotenv"
AUTHOR = "Saurabh Kumar <thes.kumar+web@gmail.com>", "Bertrand Bonnefoy-Claudet <bertrand@bertrandbc.com>"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=e914cdb773ae44a732b392532d88f072"

SRC_URI[md5sum] = "68abb78e05460ce558ca255de550e1ea"
SRC_URI[sha256sum] = "e324ee90a023d808f1959c46bcbc04446a10ced277783dc6ee09987c37ec10ca"

PYPI_PACKAGE = "python-dotenv"
inherit pypi setuptools3

S = "${WORKDIR}/python-dotenv-${PV}"

# RDEPENDS:${PN} = "\
# "

SUMMARY = "Simple sunset and sunrise time calculation python library"
HOMEPAGE = "https://github.com/SatAgro/suntime"
AUTHOR = "Krzysztof Stopa"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=e6a600fd5e1d9cbde2d983680233ad02"

SRC_URI[sha256sum] = "4834f7907ad13dbb369904cb5f4376edc0b06c6e8a1cfc0aac1268f64d0ecdcf"

PYPI_PACKAGE = "suntime"
inherit pypi setuptools3

S = "${WORKDIR}/suntime-${PV}"

# RDEPENDS:${PN} = "\
# "

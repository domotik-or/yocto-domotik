SUMMARY = "Raspberry Pi GPIO module"
HOMEPAGE = "http://abyz.me.uk/rpi/pigpio/python.html"
AUTHOR = "joan <joan@abyz.me.uk>"
LICENSE = "Unlicense"
LIC_FILES_CHKSUM = "file://setup.py;md5=2ac077804713ae112a818a10ae0527dc"

SRC_URI[md5sum] = "e05e3f5cb6c50f4b3eeb4ac50627507e"
SRC_URI[sha256sum] = "91efa50e4990649da97408a384782d6ccf58342fc59cdfe21ed7a42911569975"

S = "${WORKDIR}/pigpio-${PV}"

RDEPENDS_${PN} = "pigpio-bin-gpiod"

PYPI_PACKAGE = "pigpio"

inherit pypi setuptools3

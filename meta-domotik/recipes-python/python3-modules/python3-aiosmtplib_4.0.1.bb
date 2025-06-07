SUMMARY = "aiosmtplib is an asynchronous SMTP client for use with asyncio"
HOMEPAGE = "https://github.com/cole/aiosmtplib"
AUTHOR = "Cole Maclean <hi@colemaclean.dev>"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=2b9776f33ed0a7fe8823d389e2d7c539"

SRC_URI[sha256sum] = "10d426afe923edeb28ce0f007da0ee4060e9e12dd3890c162b22e1958da35761"

PYPI_PACKAGE = "aiosmtplib"
inherit pypi python_hatchling

S = "${WORKDIR}/aiosmtplib-${PV}"

# RDEPENDS:${PN} = "\
# "

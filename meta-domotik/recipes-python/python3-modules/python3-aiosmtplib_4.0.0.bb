SUMMARY = "aiosmtplib is an asynchronous SMTP client for use with asyncio"
HOMEPAGE = "https://github.com/cole/aiosmtplib"
AUTHOR = "Cole Maclean <hi@colemaclean.dev>"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=2b9776f33ed0a7fe8823d389e2d7c539"

SRC_URI[md5sum] = "030e706478aa9ece23e1167558a41a5a"
SRC_URI[sha256sum] = "9629a0d8786ab1e5f790ebbbf5cbe7886fedf949a3f52fd7b27a0360f6233422"

PYPI_PACKAGE = "aiosmtplib"
inherit pypi setuptools3

S = "${WORKDIR}/aiosmtplib-${PV}"

# RDEPENDS:${PN} = "\
# "

do_configure:prepend() {
cat > ${S}/setup.py <<-EOF
from setuptools import setup

setup(
   name="${PYPI_PACKAGE}",
   version="${PV}",
   license="${LICENSE}",
)
EOF
}

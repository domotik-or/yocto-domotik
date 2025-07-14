SUMMARY = "QB styles for common plotting libraries"
HOMEPAGE = "https://github.com/quantumblacklabs/qbstyles"
AUTHOR = "QuantumBlack Labs <opensource@quantumblack.com>"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://setup.py;beginline=53;endline=53;md5=cdf6578f2a5422808723dacffd139bb4"

SRC_URI[sha256sum] = "03bf00a6c03559cb2d62cbebef4c105989e9addd64df79980c373fb17dce6d80"

PYPI_PACKAGE = "qbstyles"
inherit pypi setuptools3

S = "${WORKDIR}/qbstyles-${PV}"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI += "\
    file://0001-qbstyle.patch \
"

# RDEPENDS:${PN} = "\
# "

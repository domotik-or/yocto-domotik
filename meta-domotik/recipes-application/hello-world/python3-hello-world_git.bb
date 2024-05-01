SUMMARY = "A simple program, and an example of how to structure a python project"
AUTHOR = "David Barnett"
LICENSE = "CLOSED"
# LICENSE = "Apache-2.0"
# LIC_FILES_CHKSUM = "file://LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57"

# in development phase, use the latest commit
SRCREV = "${AUTOREV}"
PV = "0.1.0+git${SRCPV}"

# SRCREV = "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"
SRC_URI = "http://github.com/dbarnett/python-helloworld;protocol=https"
SRC_URI[sha256sum] = "75898a7a850f8d8947ca91122abb505b424d5aa9eba999e7b371c94666c7a55e"

S = "${WORKDIR}/git"

inherit setuptools3

SUMMARY = "Implementation of semantic version"
HOMEPAGE = "https://github.com/keleshev/version"
AUTHOR = "Vladimir Keleshev <vladimir@keleshev.com>"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE-MIT;md5=f3d76ccd9090eb8ea884412dfce348b4"

SRCREV = "${AUTOREV}"
PV = "0.1.1+git${SRCPV}"

SRC_URI = "git://github.com/keleshev/version.git;branch=master;protocol=https"
SRC_URI[sha256sum] = "e19500d7e89c80d6bae07ee2992d49f089ee6bf260585f1b66f00e8bda284d53"

S = "${WORKDIR}/git"

# PYPI_PACKAGE = "version"
inherit setuptools3

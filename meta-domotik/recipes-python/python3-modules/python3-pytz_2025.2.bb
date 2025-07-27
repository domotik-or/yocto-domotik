SUMMARY = "World timezone definitions, modern and historical"
HOMEPAGE = "https://pythonhosted.org/pytz/"
AUTHOR = "	Stuart Bishop <stuart@stuartbishop.net>"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=1a67fc46c1b596cce5d21209bbe75999"

SRC_URI[sha256sum] = "360b9e3dbb49a209c21ad61809c7fb453643e048b38924c765813546746e81c3"

PYPI_PACKAGE = "pytz"
inherit pypi setuptools3

S = "${WORKDIR}/pytz-${PV}"

# RDEPENDS:${PN} = "\
# "

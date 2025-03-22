SUMMARY = "The idiomatic asyncio MQTT client, wrapped around paho-mqtt"
HOMEPAGE = "https://github.com/sbtinstruments/aiomqtt"
AUTHOR = "Frederik Aalund <fpa@sbtinstruments.com>", "Felix Böhm <felix@felixboehm.dev>", "Jonathan Plasse <jonathan.plasse@live.fr>">"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=a462083fa4d830bdcf8c22a8ddf453cf"

SRC_URI[md5sum] = "83a05e30a73feb6131a064f83e67a710"
SRC_URI[sha256sum] = "8501c373c00fa7074a990a951285f31340c29b6541aea15e040da9ea33d79719"

PYPI_PACKAGE = "aiomqtt"
inherit pypi setuptools3

S = "${WORKDIR}/aiomqtt-${PV}"

RDEPENDS:${PN} = "\
    ${PYTHON_PN}-paho-mqtt \
"

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

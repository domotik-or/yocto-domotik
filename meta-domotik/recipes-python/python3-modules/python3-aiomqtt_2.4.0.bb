SUMMARY = "The idiomatic asyncio MQTT client, wrapped around paho-mqtt"
HOMEPAGE = "https://github.com/sbtinstruments/aiomqtt"
AUTHOR = "Frederik Aalund <fpa@sbtinstruments.com>", "Felix Böhm <felix@felixboehm.dev>", "Jonathan Plasse <jonathan.plasse@live.fr>"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=a462083fa4d830bdcf8c22a8ddf453cf"

SRC_URI[sha256sum] = "ab0f18fc5b7ffaa57451c407417d674db837b00a9c7d953cccd02be64f046c17"

PYPI_PACKAGE = "aiomqtt"
inherit pypi python_hatchling

S = "${WORKDIR}/aiomqtt-${PV}"

RDEPENDS:${PN} = "\
    ${PYTHON_PN}-paho-mqtt \
"

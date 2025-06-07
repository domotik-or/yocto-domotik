SUMMARY = "An asyncio PostgreSQL driver"
HOMEPAGE = "ihttps://github.com/MagicStack/asyncpg"
AUTHOR = "Elvis Pranskevichus", "edgedb-ci", "magicstack-ci", "Yury Selivanov <yury@edgedb.com>"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=813b34b25fe7db380a4467c87e5728c9"

SRC_URI[sha256sum] = "c551e9928ab6707602f44811817f82ba3c446e018bfe1d3abecc8ba5f3eac851"

PYPI_PACKAGE = "asyncpg"
# inherit pypi python_flit_core
inherit pypi setuptools3

S = "${WORKDIR}/asyncpg-${PV}"

RDEPENDS:${PN} = "\
    ${PYTHON_PN}-async-timeout \
    ${PYTHON_PN}-version \
"

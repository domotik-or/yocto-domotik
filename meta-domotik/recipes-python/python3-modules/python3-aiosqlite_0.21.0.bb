SUMMARY = "aiosqlite provides a friendly, async interface to sqlite databases."
HOMEPAGE = "https://github.com/omnilib/aiosqlite"
AUTHOR = "Amethyst Reese <amethyst@n7.gg>"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=f0c422eaa1f23d09f8203dc0af3e2d54"

SRC_URI[sha256sum] = "131bb8056daa3bc875608c631c678cda73922a2d4ba8aec373b19f18c17e7aa3"

PYPI_PACKAGE = "aiosqlite"
inherit pypi python_hatchling

S = "${WORKDIR}/aiosqlite-${PV}"

# RDEPENDS:${PN} = "\
# "

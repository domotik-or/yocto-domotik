DESCRIPTION = "This is a simple Hello World recipe - extracts a local tarball and then uses CMake to build it"
HOMEPAGE = "https://kickstartembedded.com"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=45269dcabf49617cca580ad6878cbcd2"

SRC_URI = "file://hwtarlocal-${PV}.tar.gz"

inherit cmake

EXTRA_OECMAKE = ""

do_install() {
	install -d ${D}${bindir}
	install -m 0755 hello-world-git ${D}${bindir}
}

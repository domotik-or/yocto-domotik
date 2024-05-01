DESCRIPTION = "This is a simple Hello World recipe - downloads the application from github and uses CMake to build it"
HOMEPAGE = "https://kickstartembedded.com"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=45269dcabf49617cca580ad6878cbcd2"

SRCREV = "0d3a30ec83ea9c748e31769921f85665ffdd2fb5"
PV = "0.1+git${SRCPV}"
SRC_URI = "git://github.com/sckulkarni246/yocto-test-apps"

S = "${WORKDIR}/git"

inherit cmake

EXTRA_OECMAKE = ""

do_install() {
	install -d ${D}${bindir}
	install -m 0755 hello-world-git ${D}${bindir}
}

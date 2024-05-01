# We base this recipe on core-image-minimal core image
require recipes-core/images/core-image-minimal.bb

# Adding a description is optional but often helps the user understand the purpose of the image
DESCRIPTION = "This is a customized version of the core-image-minimal image available in poky - this contains dev packages and installs the custom hello-world recipes present in meta-ke."

# We specify here the additional recipes to be added to the build
IMAGE_INSTALL += "hwlocal hw"

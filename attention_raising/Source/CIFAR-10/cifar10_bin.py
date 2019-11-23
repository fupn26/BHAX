from PIL import Image
import numpy as np
import sys
im = Image.open(sys.argv[1])
im = (np.array(im))
r = im[:,:,0].flatten()
g = im[:,:,1].flatten()
b = im[:,:,2].flatten()
label = [0]
out = np.array(list(label) + list(r) + list(g) + list(b),np.uint8)
out.tofile("./cifar10_data/cifar-10-batches-bin/" + sys.argv[2])
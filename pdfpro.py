# !/usr/bin/python

import fitz

input_file = "data/201128.pdf"
output_file = "example-with-barcode.pdf"
barcode_file = "data/dynaimc.jpg"

# define the position (upper-right corner)
image_rectangle = fitz.Rect(450,20,550,120)

# retrieve the first page of the PDF
file_handle = fitz.open(input_file)
first_page = file_handle[0]

# add the image
first_page.insertImage(image_rectangle, fileName=barcode_file)

file_handle.save(output_file)
# test

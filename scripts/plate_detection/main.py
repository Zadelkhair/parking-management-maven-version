import cv2
import numpy as np
import imutils
import easyocr
import sys
import uuid

if __name__ == '__main__':
    ## 1. Read in Image, Grayscale and Blur
    #http://192.168.1.4:8080/video/mjpeg
    url = sys.argv[1]
    type = sys.argv[2]
    img = None
    if type == 'ip' :
        capture = cv2.VideoCapture(url)
        ret, frame = capture.read()
        img = frame
    elif type == 'img' :
        img = cv2.imread(url)

    gray = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)

    ## 2. Apply filter and find edges for localization
    bfilter = cv2.bilateralFilter(gray, 11, 17, 17)  # Noise reduction
    edged = cv2.Canny(bfilter, 30, 200)  # Edge detection

    ## 3. Find Contours and Apply Mask
    keypoints = cv2.findContours(edged.copy(), cv2.RETR_TREE, cv2.CHAIN_APPROX_SIMPLE)
    contours = imutils.grab_contours(keypoints)
    contours = sorted(contours, key=cv2.contourArea, reverse=True)[:10]

    location = None
    for contour in contours:
        approx = cv2.approxPolyDP(contour, 10, True)
        if len(approx) == 4:
            location = approx
            break

    mask = np.zeros(gray.shape, np.uint8)
    new_image = cv2.drawContours(mask, [location], 0, 255, -1)
    new_image = cv2.bitwise_and(img, img, mask=mask)
    (x, y) = np.where(mask == 255)
    (x1, y1) = (np.min(x), np.min(y))
    (x2, y2) = (np.max(x), np.max(y))
    cropped_image = gray[x1:x2 + 1, y1:y2 + 1]

    ## 4. Use Easy OCR To Read Text
    reader = easyocr.Reader(['en'])
    result = reader.readtext(cropped_image)

    ## 5. Render Result
    text = result[0][-2]
    font = cv2.FONT_HERSHEY_SIMPLEX
    res = cv2.putText(img, text=text, org=(approx[0][0][0], approx[1][0][1] + 60), fontFace=font, fontScale=1, color=(0, 255, 0), thickness=2, lineType=cv2.LINE_AA)
    res = cv2.rectangle(img, tuple(approx[0][0]), tuple(approx[2][0]), (0, 255, 0), 3)

    out_file_name = 'out/'+str(uuid.uuid4())+'.jpg'
    cv2.imwrite(out_file_name,res)
    print('out_plate:'+text)
    print('out_file:'+out_file_name)





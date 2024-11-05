package mn.astvision.starter.service;

import mn.astvision.starter.exception.ContentTypeException;
import mn.astvision.starter.s3.ImageType;
import mn.astvision.starter.s3.ImageConfig;
import mn.astvision.starter.util.image.ImageUtil;

import java.io.InputStream;

public class ImageService {

    public static byte[] resize(InputStream inputStream, ImageType imageType, String contentType)
            throws Exception {
        if (imageType == null || !ImageConfig.ImageWidthResize.containsKey(imageType))
            return inputStream.readAllBytes();

        return ImageUtil.resizeWithCompress(
                inputStream,
                ImageConfig.ImageWidthResize.get(imageType),
                contentType
        );
    }

    public static byte[] resize(byte[] bytes, ImageType imageType, String contentType) throws Exception {
        if (imageType == null || !ImageConfig.ImageWidthResize.containsKey(imageType))
            return bytes;

        return ImageUtil.resizeWithCompress(
                bytes,
                ImageConfig.ImageWidthResize.get(imageType),
                contentType
        );
    }

    public static void checkContentType(ImageType imageType, String contentType) throws ContentTypeException {
        if (imageType == null || !ImageConfig.FileContentType.containsKey(imageType))
            return;

        if (!ImageConfig.FileContentType.get(imageType).contains(contentType))
            throw new ContentTypeException(ImageConfig.FileContentTypeError.get(imageType));
    }
}

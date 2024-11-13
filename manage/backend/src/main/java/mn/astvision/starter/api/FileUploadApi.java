//package mn.astvision.starter.api;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import mn.astvision.starter.annotations.AuthUser;
//import mn.astvision.starter.constants.GlobalDateFormat;
//import mn.astvision.starter.dto.MoveFileRequest;
//import mn.astvision.starter.model.auth.User;
//import mn.astvision.starter.s3.service.S3BucketFolder;
//import mn.astvision.starter.s3.service.S3BucketService;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.annotation.Secured;
//import org.springframework.util.ObjectUtils;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.time.LocalDateTime;
//import java.util.Map;
//
///**
// * @author digz6666
// */
//@Slf4j
//@Secured({"ROLE_DEFAULT"})
//@RestController
//@RequestMapping("/v1/file")
//@RequiredArgsConstructor
//public class FileUploadApi extends BaseController {
//
//    private final S3BucketService s3BucketService;
//
//    @PostMapping(value = "upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
//    public ResponseEntity<?> upload(
//            @RequestPart("file") MultipartFile file,
//            @RequestParam(required = false) String entity,
//            @RequestParam(required = false, defaultValue = "true") Boolean temp,
//            @AuthUser User user
//    ) {
//        if (ObjectUtils.isEmpty(file))
//            return badRequestMessage("Файл оруулна уу");
//
//        try {
//            StringBuilder fileKeyBuilder = new StringBuilder();
//            StringBuilder fileNameBuilder = new StringBuilder();
//            if (temp) {
//                fileKeyBuilder.append(S3BucketFolder.TEMP);
//                fileNameBuilder
//                        .append(GlobalDateFormat.DATE_TIME_FILE.format(LocalDateTime.now()))
//                        .append("_")
//                        .append(file.getOriginalFilename());
//            } else {
//                if (!ObjectUtils.isEmpty(entity)) {
//                    fileKeyBuilder.append(entity);
//                    if (!entity.endsWith("/"))
//                        fileKeyBuilder.append("/");
//                }
//                fileNameBuilder.append(file.getOriginalFilename());
//            }
//            fileKeyBuilder.append(fileNameBuilder);
//
//            String url = s3BucketService.upload(
//                    fileKeyBuilder.toString(),
//                    fileNameBuilder.toString(),
//                    file.getContentType(),
//                    file.getBytes(),
//                    file.getSize(),
//                    Map.of(
//                            "userId", user.getId(),
//                            "key", fileKeyBuilder.toString(),
//                            "name", fileNameBuilder.toString(),
//                            "entity", ObjectUtils.isEmpty(entity) ? "" : entity
//                    )
//            );
//
//            if (ObjectUtils.isEmpty(url))
//                return serverErrorMessage("Зураг оруулахад алдаа гарлаа");
//
//            return ResponseEntity.ok(url.replaceAll("https://nepko-bucket.s3.ap-southeast-1.amazonaws.com", "https://cdn.nepkokids.mn"));
//
//        } catch (Exception e) {
//            log.error("file upload entity: " + entity + ", error: " + e);
//            return serverErrorMessage("Системийн алдаа гарлаа");
//        }
//    }
//
//    @PostMapping(value = "upload-move", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
//    public ResponseEntity<?> uploadTemp(
//            @RequestPart("file") MultipartFile file,
//            @RequestParam(required = false) String entity,
//            @RequestParam(required = false, defaultValue = "true") Boolean temp,
//            @RequestParam(required = false) String moveKey,
//            @RequestParam(required = false) String moveName,
//            @AuthUser User user
//    ) {
//        if (ObjectUtils.isEmpty(file))
//            return badRequestMessage("Файл оруулна уу");
//
//        try {
//            StringBuilder fileKeyBuilder = new StringBuilder();
//            StringBuilder fileNameBuilder = new StringBuilder();
//            if (temp) {
//                fileKeyBuilder.append(S3BucketFolder.TEMP);
//                fileNameBuilder
//                        .append(GlobalDateFormat.DATE_TIME_FILE.format(LocalDateTime.now()))
//                        .append("_")
//                        .append(file.getOriginalFilename());
//            } else {
//                if (!ObjectUtils.isEmpty(entity)) {
//                    fileKeyBuilder.append(entity);
//                    if (!entity.endsWith("/"))
//                        fileKeyBuilder.append("/");
//                }
//                fileNameBuilder.append(file.getOriginalFilename());
//            }
//            fileKeyBuilder.append(fileNameBuilder);
//
//            String url = s3BucketService.upload(
//                    fileKeyBuilder.toString(),
//                    fileNameBuilder.toString(),
//                    file.getContentType(),
//                    file.getBytes(),
//                    file.getSize(),
//                    Map.of(
//                            "userId", user.getId(),
//                            "key", fileKeyBuilder.toString(),
//                            "name", fileNameBuilder.toString(),
//                            "entity", ObjectUtils.isEmpty(entity) ? "" : entity
//                    )
//            );
//
//            if (ObjectUtils.isEmpty(url))
//                return serverErrorMessage("Зураг оруулахад алдаа гарлаа");
//
//            MoveFileRequest mfRequestThumb = MoveFileRequest.builder()
//                    .entity(moveKey)
//                    .dataId("thumb")
//                    .name(moveName)
//                    .fileUrl(url)
//                    .build();
//            String newUrl = s3BucketService.moveTempFile(mfRequestThumb);
//
//            return ResponseEntity.ok(Map.of("url", url, "movedUrl", newUrl));
//        } catch (Exception e) {
//            log.error("file upload entity: " + entity + ", error: " + e);
//            return serverErrorMessage("Системийн алдаа гарлаа");
//        }
//    }
//}

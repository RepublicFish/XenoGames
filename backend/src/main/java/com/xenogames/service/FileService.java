package com.xenogames.service;

import com.xenogames.common.exception.BusinessException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Service
public class FileService {

    @Value("${app.upload.path:./uploads}")
    private String uploadPath;

    @Value("${app.upload.cover-dir:covers}")
    private String coverDir;

    @Value("${app.upload.screenshot-dir:screenshots}")
    private String screenshotDir;

    private static final Set<String> ALLOWED_EXTENSIONS = Set.of("jpg", "jpeg", "png", "webp");

    /**
     * 上传文件
     * @param file 文件
     * @param type cover | screenshot
     * @return 访问 URL
     */
    public Map<String, String> uploadFile(MultipartFile file, String type) {
        if (file.isEmpty()) {
            throw new BusinessException(400, "文件不能为空");
        }

        // 校验文件扩展名
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null) {
            throw new BusinessException(400, "文件名不能为空");
        }
        String extension = getExtension(originalFilename);
        if (!ALLOWED_EXTENSIONS.contains(extension.toLowerCase())) {
            throw new BusinessException(400, "不支持的文件格式，仅允许: " + String.join(", ", ALLOWED_EXTENSIONS));
        }

        // 确定存储子目录
        String subDir = "screenshot".equals(type) ? screenshotDir : coverDir;
        String filename = UUID.randomUUID().toString() + "." + extension.toLowerCase();

        try {
            Path targetDir = Paths.get(uploadPath, subDir);
            Files.createDirectories(targetDir);
            Path targetPath = targetDir.resolve(filename);
            file.transferTo(targetPath.toFile());

            String url = "/uploads/" + subDir + "/" + filename;
            return Map.of("url", url);
        } catch (IOException e) {
            throw new BusinessException(500, "文件上传失败: " + e.getMessage());
        }
    }

    private String getExtension(String filename) {
        int dotIndex = filename.lastIndexOf('.');
        if (dotIndex == -1) {
            return "";
        }
        return filename.substring(dotIndex + 1);
    }
}

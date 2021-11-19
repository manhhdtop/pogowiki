package com.pogowiki.service;

import com.pogowiki.exception.FileStorageException;
import com.pogowiki.model.response.UploadFileResponse;
import com.pogowiki.utils.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.Objects;


@Service
public class FileService {
    private final Path fileStorageLocation;
    public final String rootPath;
    public final String uploadFolder;

    @Autowired
    public FileService(@Value("${file.root-path}") String rootPath, @Value("${file.upload-dir}") String uploadFolder) {
        this.rootPath = rootPath;
        this.uploadFolder = uploadFolder;
        this.fileStorageLocation = Paths.get(rootPath + uploadFolder).toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    public UploadFileResponse storeFile(MultipartFile file) {
        return storeFile(file, uploadFolder);
    }

    public UploadFileResponse storeFile(MultipartFile file, String path) {
        if (!path.startsWith("/")) {
            path = "/" + path;
        }
        return storeFile(file, Paths.get(rootPath + uploadFolder + path).toAbsolutePath().normalize(), path);
    }

    public UploadFileResponse storeFile(MultipartFile file, String path, String filename) {
        if (!path.startsWith("/")) {
            path = "/" + path;
        }
        return storeFile(file, Paths.get(rootPath + uploadFolder + path).toAbsolutePath().normalize(), path, filename);
    }

    private UploadFileResponse storeFile(MultipartFile file, Path path, String folderUpload) {
        return storeFile(file, path, folderUpload, null);
    }

    private UploadFileResponse storeFile(MultipartFile file, Path path, String folderUpload, String fileName) {
        if (fileName == null) {
            fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        }

        try {
            Files.createDirectories(path);
        } catch (Exception ex) {
            throw new FileStorageException("file.upload_folder.create_fail", ex);
        }

        try {
            // Check if the file's name contains invalid characters
            if (fileName.contains("..")) {
                throw new FileStorageException("file.filename.invalid");
            }

            // Copy file to the target location (Replacing existing file with the same name)
            int index = fileName.lastIndexOf(".");
            String fileUpload;
            if (index == -1) {
                fileUpload = new Date().getTime() + "";
            } else {
                String fileExtension = fileName.substring(index + 1);
                fileUpload = new Date().getTime() + "." + fileExtension;
            }
            String fileDownloadUri = uploadFolder + folderUpload + "/" + fileUpload;

            Path targetLocation = path.toAbsolutePath().normalize().resolve(fileUpload);

            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return UploadFileResponse.of(fileName, fileDownloadUri, file.getContentType(), file.getSize());
        } catch (IOException ex) {
            throw new FileStorageException(MessageUtils.getMessage("file.store_file.error", fileName), ex);
        } catch (Exception e) {
            throw new FileStorageException(MessageUtils.getMessage("file.invalid" + fileName));
        }
    }

    public File getFile(String url) {
        String uri = rootPath + url;
        File file = new File(uri);
        if (file.exists()) {
            return file;
        }
        return null;
    }

    public byte[] readFile(String url) {
        File file = getFile(url);
        if (file == null) {
            return null;
        }
        byte[] bytes = null;
        try (InputStream is = new FileInputStream(file)) {

            long length = file.length();
            if (length > Integer.MAX_VALUE) {
                return null;
            }
            bytes = new byte[(int) length];

            int offset = 0;
            int numRead;
            while (offset < bytes.length
                    && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
                offset += numRead;
            }

            if (offset < bytes.length) {
                return null;
            }
        } catch (Exception ignored) {
        }
        return bytes;
    }
}

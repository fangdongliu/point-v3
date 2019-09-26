package cn.fdongl.point.uploadapiimpl.service;

import cn.fdongl.point.auth.vo.JwtUser;
import cn.fdongl.point.common.entity.File;
import cn.fdongl.point.common.util.PageResult;
import cn.fdongl.point.uploadapi.service.FileService;
import cn.fdongl.point.uploadapiimpl.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.file.Files;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    FileRepository fileRepository;

    String path = "./ptstore";

    @Override
    public PageResult page(Pageable pageable, String type, JwtUser user) {
        return PageResult.ofPage(
                fileRepository.findByCreateByAndType(user.getId(),type,pageable));
    }

    @Override
    public void download(int fileId,HttpServletResponse response) throws FileNotFoundException {
        File file = fileRepository.getOne((long) fileId);
        java.io.File dest = new java.io.File(path + java.io.File.separator + fileId);
        if (dest.exists()) {
            response.setContentType("application/msexcel");
            try {
                response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(file.getFilename(), "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            try {
                InputStream in = new FileInputStream(dest);
                ServletOutputStream out = response.getOutputStream();
                final byte[] b = new byte[8192];
                for (int r; (r = in.read(b)) != -1; ) {
                    out.write(b, 0, r);
                }
                in.close();
                out.flush();
                out.close();
            } catch (IOException e) {
                throw new FileNotFoundException();
            }
        }
    }

    @Override
    public File save(MultipartFile file,String type) throws IOException {
        File f=new File()
                .setType(type)
                .setFilename(file.getOriginalFilename());
        fileRepository.save(f);
        java.io.File dest = new java.io.File(path+ java.io.File.separator + f.getId());
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        FileCopyUtils.copy(file.getInputStream(), Files.newOutputStream(dest.toPath()));
        return f;
    }

    @Override
    public void delete(long fileId) {
        fileRepository.deleteById(fileId);
    }

}

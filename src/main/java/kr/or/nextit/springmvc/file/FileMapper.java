package kr.or.nextit.springmvc.file;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FileMapper {
    void saveFile(List<FileVO> files);
}

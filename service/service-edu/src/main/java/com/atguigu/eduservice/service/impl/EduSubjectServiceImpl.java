package com.atguigu.eduservice.service.impl;

        import com.alibaba.excel.EasyExcel;
        import com.atguigu.eduservice.entity.EduSubject;
        import com.atguigu.eduservice.entity.excel.ExcelData;
        import com.atguigu.eduservice.entity.subject.OneSubject;
        import com.atguigu.eduservice.entity.subject.TwoSubject;
        import com.atguigu.eduservice.listener.SubjectListener;
        import com.atguigu.eduservice.mapper.EduSubjectMapper;
        import com.atguigu.eduservice.service.EduSubjectService;
        import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
        import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
        import org.springframework.beans.BeanUtils;
        import org.springframework.stereotype.Service;
        import org.springframework.web.multipart.MultipartFile;

        import javax.security.auth.Subject;
        import java.io.InputStream;
        import java.util.ArrayList;
        import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-06-19
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    @Override
    public void saveSubject(MultipartFile file,EduSubjectService eduSubjectService) {
        try {
            InputStream in = file.getInputStream();
            EasyExcel.read(in, ExcelData.class,new SubjectListener(eduSubjectService)).sheet().doRead();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public List<OneSubject> getAllOneSubject() {

        //一级分类查询
        QueryWrapper<EduSubject>  OneSubjectQueryWrapper = new QueryWrapper<>();
        OneSubjectQueryWrapper.eq("parent_id","0");
        List<EduSubject> Onelist = baseMapper.selectList(OneSubjectQueryWrapper);

        //二级分类查询
        QueryWrapper<EduSubject> TwoSubjectQueryWrapper  = new QueryWrapper<>();
        TwoSubjectQueryWrapper.ne("parent_id","0");
        List<EduSubject> TwoSubjects = baseMapper.selectList(TwoSubjectQueryWrapper);

        //一级分类
        List<OneSubject> oneSubjectList = new ArrayList<>();




        for (int i=0;i<Onelist.size();i++) {

            EduSubject eduSubject = Onelist.get(i);
            OneSubject oneSubject = new OneSubject();
            //  BeanUtils.copyProperties(eduSubject,oneSubject);吧前面的对象相同的属性复制到后面的对象
            BeanUtils.copyProperties(eduSubject, oneSubject);


            List<TwoSubject> twoSubjectList = new ArrayList<>();

            //判断二级分类属于哪一个一级分类下，并添加
            for (int m = 0; m < TwoSubjects.size(); m++) {
                EduSubject twoEduSubject = TwoSubjects.get(m);
                if(twoEduSubject.getParentId().equals(oneSubject.getId())){
                    TwoSubject twoSubject = new TwoSubject();
                    BeanUtils.copyProperties(twoEduSubject,twoSubject);
                    twoSubjectList.add(twoSubject);
                }
            }

            oneSubject.setChildren(twoSubjectList);
            oneSubjectList.add(oneSubject);
        }
        return oneSubjectList;
    }
}

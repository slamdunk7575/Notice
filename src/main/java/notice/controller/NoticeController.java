package notice.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import notice.domain.NoticeEntity;
import notice.domain.NoticeFileEntity;
import notice.dto.NoticeDto;
import notice.service.NoticeService;
import org.apache.commons.io.FileUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.net.URLEncoder;
import java.util.List;
import java.util.function.Function;

@Slf4j
@Controller
@AllArgsConstructor
public class NoticeController {

    private NoticeService noticeService;


    /*@GetMapping(value = "/api/notice")
    public ModelAndView openNoticeList(ModelMap model) throws Exception {

        ModelAndView mv = new ModelAndView("notice/noticeList");
        List<NoticeDto> list = noticeService.selectNoticeList();
        mv.addObject("list", list);
        return mv;
    }*/


    @GetMapping(value = "/api/notice")
    public ModelAndView openNoticeListWithPage(@PageableDefault Pageable pageable, ModelMap model) throws Exception {

        ModelAndView mv = new ModelAndView("notice/noticeList");
        Page<NoticeEntity> entityList = noticeService.selectNoticeListWithPage(pageable);

        Page<NoticeDto> list = entityList.map(new Function<NoticeEntity, NoticeDto>() {
            @Override
            public NoticeDto apply(NoticeEntity entity) {
                return entity.toDto();
            }
        });

        mv.addObject("list", list);

        log.debug("총 element 수 : {}, 전체 page 수 : {}, 페이지에 표시할 element 수 : {}, 현재 페이지 index : {}, 현재 페이지의 element 수 : {}",
                list.getTotalElements(), list.getTotalPages(), list.getSize(),
                list.getNumber(), list.getNumberOfElements());

        return mv;
    }


    // @GetMapping(value = "/api/notice/write")
    @RequestMapping(value="/api/notice/write", method=RequestMethod.GET)
    public String openNoticeWrite() throws Exception {
        return "notice/noticeWrite";
    }


    // @PostMapping(value = "/api/notice/write")
    @RequestMapping(value="/api/notice/write", method=RequestMethod.POST)
    public String writeNotice(NoticeDto notice, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception{
        noticeService.saveNotice(notice, multipartHttpServletRequest);
        return "redirect:/api/noticePage";
    }


    @GetMapping(value = "/api/notice/{noticeIdx}")
    public ModelAndView openNoticeDetail(@PathVariable("noticeIdx") Long noticeIdx) throws Exception {

        ModelAndView mv = new ModelAndView("notice/noticeDetail");
        NoticeDto notice = noticeService.selectNoticeDetail(noticeIdx);
        mv.addObject("notice", notice);

        return mv;
    }


    @PutMapping(value = "/api/notice/{noticeIdx}")
    public String updateNotice(NoticeDto notice) throws Exception {
        noticeService.saveNotice(notice, null);
        return "redirect:/api/noticePage";
    }


    @DeleteMapping(value = "/api/notice/{noticeIdx}")
    public String deleteNotice(@PathVariable("noticeIdx") Long noticeIdx) throws Exception {
        noticeService.deleteNotice(noticeIdx);
        return "redirect:/api/noticePage";
    }


    @RequestMapping(value="/api/notice/file", method=RequestMethod.GET)
    public void downloadNoticeFile(Long noticeIdx, Long idx, HttpServletResponse response) throws Exception{
        NoticeFileEntity file = noticeService.selectNoticeFileInformation(noticeIdx, idx);

        byte[] files = FileUtils.readFileToByteArray(new File(file.getStoredFilePath()));

        response.setContentType("application/octet-stream");
        response.setContentLength(files.length);
        response.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(file.getOriginalFileName(),"UTF-8")+"\";");
        response.setHeader("Content-Transfer-Encoding", "binary");

        response.getOutputStream().write(files);
        response.getOutputStream().flush();
        response.getOutputStream().close();
    }

}

package notice.controller;

import notice.entity.NoticeEntity;
import notice.entity.NoticeFileEntity;
import notice.service.NoticeService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.net.URLEncoder;
import java.util.List;

// @Controller
public class NoticeController {

    private final NoticeService noticeService;

    @Autowired
    public NoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }


    @GetMapping(value = "/api/notice")
    public ModelAndView openNoticeList(ModelMap model) throws Exception {

        ModelAndView mv = new ModelAndView("/notice/noticeList");
        List<NoticeEntity> list = noticeService.selectNoticeList();
        mv.addObject("list", list);

        return mv;
    }

    // @GetMapping(value = "/api/notice/write")
    @RequestMapping(value="/api/notice/write", method=RequestMethod.GET)
    public String openNoticeWrite() throws Exception {
        return "/notice/noticeWrite";
    }

    // @PostMapping(value = "/api/notice/write")
    @RequestMapping(value="/api/notice/write", method=RequestMethod.POST)
    public String writeNotice(NoticeEntity notice, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception{
        noticeService.saveNotice(notice, multipartHttpServletRequest);
        return "redirect:/api/notice";
    }

    @GetMapping(value = "/api/notice/{noticeIdx}")
    public ModelAndView openNoticeDetail(@PathVariable("noticeIdx") int noticeIdx) throws Exception {

        ModelAndView mv = new ModelAndView("/notice/noticeDetail");
        NoticeEntity notice = noticeService.selectNoticeDetail(noticeIdx);
        mv.addObject("notice", notice);

        return mv;
    }

    @PutMapping(value = "/api/notice/{noticeIdx}")
    public String updateNotice(NoticeEntity notice) throws Exception {
        noticeService.saveNotice(notice, null);
        return "redirect:/api/notice";
    }

    @DeleteMapping(value = "/api/notice/{noticeIdx}")
    public String deleteNotice(@PathVariable("noticeIdx") int noticeIdx) throws Exception {
        noticeService.deleteNotice(noticeIdx);
        return "redirect:/api/notice";
    }

    @RequestMapping(value="/api/notice/file", method=RequestMethod.GET)
    public void downloadNoticeFile(int noticeIdx, int idx, HttpServletResponse response) throws Exception{
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




    /*@GetMapping(value = "/api/notice")
    public List<NoticeEntity> openNoticeList() throws Exception {
        return noticeService.selectNoticeList();
    }

    @GetMapping(value = "/api/notice/write")
    public String openNoticeWrite() {
        return "/notice/restNoticeWrite";
    }

    @PostMapping(value = "/api/notice/write")
    public void writeNotice (@RequestBody NoticeEntity notice) throws Exception {
        noticeService.saveNotice(notice, null);
    }

    @RequestMapping(value = "/api/notice/{noticeIdx}", method = RequestMethod.GET)
    public NoticeEntity openNoticeDetail(@PathVariable("noticeIdx")int noticeIdx) throws Exception {
        return noticeService.selectNoticeDetail(noticeIdx);
    }

    @PutMapping(value = "/api/aanoticea/{noticeIdx}")
    public String updateNotice(@RequestBody NoticeEntity noticeEntity) throws Exception {
        noticeService.saveNotice(noticeEntity, null);
        return "redirect:/notice";
    }

    @DeleteMapping(value = "/api/notice/{noticeIdx}")
    public String deleteNotice(@PathVariable("noticeIdx")int noticeIdx) throws Exception {
        noticeService.deleteNotice(noticeIdx);
        return "redirect:/board";
    }

    @RequestMapping(value = "/api/notice/file", method = RequestMethod.GET)
    public void downloadNoticeFile(@RequestParam int noticeIdx, @RequestParam int idx, HttpServletResponse response) throws Exception {
        NoticeFileEntity file = noticeService.selectNoticeFileInformation(noticeIdx, idx);

        byte[] files = FileUtils.readFileToByteArray(new File(file.getStoredFilePath()));

        response.setContentType("application/octet-stream");
        response.setContentLength(files.length);
        response.setHeader("gc", "attachment; fileName=\"" + URLEncoder.encode(file.getOriginalFileName(),"UTF-8")+"\";");
        response.setHeader("Content-Transfer-Encoding", "binary");

        response.getOutputStream().write(files);
        response.getOutputStream().flush();
        response.getOutputStream().close();
    }*/

}

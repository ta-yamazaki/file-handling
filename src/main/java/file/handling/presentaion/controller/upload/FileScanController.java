package file.handling.presentaion.controller.upload;

import file.handling.application.service.scan.VirusScanService;
import file.handling.domain.model.product.ProductExcelFile;
import file.handling.domain.model.scan.VirusScanFile;
import file.handling.domain.model.scan.VirusScanResult;
import file.handling.presentaion._error.Errors;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Controller
@RequestMapping("file-scan")
public class FileScanController {

    VirusScanService virusScanService;

    public FileScanController(VirusScanService virusScanService) {
        this.virusScanService = virusScanService;
    }

    @InitBinder("uploadFileScan")
    public void InitBinder(WebDataBinder binder) {
        binder.setAllowedFields("file");
    }

    @ModelAttribute("uploadFileScan")
    VirusScanFile uploadFileScan() {
        return new VirusScanFile();
    }

    @GetMapping
    String input() {
        return "scan/fileScan";
    }

    @PostMapping("/scan")
    String scan(@Validated @ModelAttribute("uploadFileScan") VirusScanFile uploadFileScan,
                BindingResult result,
                Model model) {
        if (result.hasErrors()) {
            Errors errors = new Errors();
            result.getAllErrors().forEach(objectError ->
                    errors.addLine(objectError.getDefaultMessage())
            );
            model.addAttribute("errorMessages", errors.message());
            return "scan/fileScan";
        }

        try {
            VirusScanResult scanResult = virusScanService.scan(uploadFileScan);
            model.addAttribute("scanResult", scanResult);
        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "ファイルの読み込みに失敗しました。");
        }
        return "scan/fileScan";
    }

    @PostMapping("/discoverVirus")
    String discoverVirus(Model model) {

        // 先頭の*はダミー
        String eicar = "*X5O!P%@AP[4\\PZX54(P^)7CC)7}$EICAR-STANDARD-ANTIVIRUS-TEST-FILE!$H+H*";
        // 先頭の*をスキップ
        InputStream fileInputStream = new ByteArrayInputStream(eicar.substring(1).getBytes());

        VirusScanResult scanResult = virusScanService.scan(fileInputStream);
        model.addAttribute("scanResult", scanResult);

        return "scan/fileScan";
    }
}

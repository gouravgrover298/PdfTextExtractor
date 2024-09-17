@RestController
@RequestMapping("/api")
public class PDFSegmenterController {

    @Autowired
    private PDFSegmenterService pdfSegmenterService;

    @PostMapping("/segment-pdf")
    public ResponseEntity<ZipFile> segmentPDF(@RequestParam("file") MultipartFile file, @RequestParam("cuts") int cuts) {
        try {
            ZipFile zipFile = pdfSegmenterService.segmentPDF(file, cuts);
            return ResponseEntity.ok(zipFile);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/pdf-metadata/{id}")
    public ResponseEntity<PDFMetadata> getPDFMetadata(@PathVariable("id") String id) {
        try {
            PDFMetadata metadata = pdfSegmenterService.getPDFMetadata(id);
            return ResponseEntity.ok(metadata);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/update-segmentation/{id}")
    public ResponseEntity<ZipFile> updateSegmentation(@PathVariable("id") String id, @RequestBody PDFSegmentationDetails details) {
        try {
            ZipFile zipFile = pdfSegmenterService.updateSegmentation(id, details);
            return ResponseEntity.ok(zipFile);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PatchMapping("/modify-segmentation/{id}")
    public ResponseEntity<ZipFile> modifySegmentation(@PathVariable("id") String id, @RequestBody PDFSegmentationDetails details) {
        try {
            ZipFile zipFile = pdfSegmenterService.modifySegmentation(id, details);
            return ResponseEntity.ok(zipFile);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/delete-pdf/{id}")
    public ResponseEntity<Void> deletePDF(@PathVariable("id") String id) {
        try {
            pdfSegmenterService.deletePDF(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
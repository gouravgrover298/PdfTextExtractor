@Service
public class PDFSegmenterService {

    @Autowired
    private PDFSegmenter pdfSegmenter;

    public ZipFile segmentPDF(MultipartFile file, int cuts) throws Exception {
        // Read PDF file
        PDDocument pdfDocument = PDDocument.load(file.getInputStream());

        // Analyze PDF content
        List<Segment> segments = pdfSegmenter.analyzePDFContent(pdfDocument, cuts);

        // Split PDF into segments
        ZipFile zipFile = new ZipFile();
        for (Segment segment : segments) {
            PDDocument segmentDocument = new PDDocument();
            segmentDocument.addPage(pdfDocument.getPage(segment.getStartPage()));
            segmentDocument.addPage(pdfDocument.getPage(segment.getEndPage()));
            zipFile.addFile(segmentDocument, segment.getFileName());
        }

        return zipFile;
    }

    public PDFMetadata getPDFMetadata(String id) throws Exception {
        // Retrieve PDF metadata from database
        PDFMetadata metadata = pdfSegmenter.getPDFMetadata(id);
        return metadata;
    }

    public ZipFile updateSegmentation(String id, PDFSegmentationDetails details) throws Exception {
        // Retrieve PDF from database
        PDDocument pdfDocument = pdfSegmenter.getPDF(id);

        // Update segmentation details
        pdfSegmenter.updateSegmentationDetails(pdfDocument, details);

        // Split PDF into segments
        ZipFile zipFile = new ZipFile();
        for (Segment segment : pdfSegmenter.getSegments(pdfDocument)) {
            PDDocument segmentDocument = new PDDocument();
            segmentDocument.addPage(pdfDocument.getPage(segment.getStartPage()));
            segmentDocument.addPage(pdfDocument.getPage(segment.getEndPage()));
            zipFile.addFile(segmentDocument, segment.getFileName());
        }

        return zipFile;
    }

    public ZipFile modifySegmentation(String id, PDFSegmentationDetails details) throws Exception {
        // Retrieve PDF from database
        PDDocument pdfDocument = pdfSegmenter.getPDF(id);

        // Modify segmentation details
        pdfSegmenter.modifySegmentationDetails(pdfDocument, details);

        // Split PDF into segments
        ZipFile zipFile = new ZipFile();
        for (Segment segment : pdfSegmenter.getSegments(pdfDocument)) {
            PDDocument segmentDocument = new PDDocument();
            segmentDocument.addPage(pdfDocument.getPage(segment.getStartPage()));
            segmentDocument.addPage(pdfDocument.getPage(segment.getEndPage()));
            zipFile.addFile(segmentDocument, segment.getFileName());
        }

        return zipFile;
    }

    public void deletePDF(String id) throws Exception {
        // Delete PDF from database
        pdfSegmenter.deletePDF(id);
    }
}
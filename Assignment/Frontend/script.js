const pdfForm = document.getElementById('pdf-form');
const pdfFileInput = document.getElementById('pdf-file');
const extractButton = document.getElementById('extract-button');
const extractedTextDiv = document.getElementById('extracted-text');

extractButton.addEventListener('click', (e) => {
    e.preventDefault();
    const pdfFile = pdfFileInput.files[0];
    const formData = new FormData();
    formData.append('file', pdfFile);

    fetch('/api/extract-text', {
        method: 'POST',
        body: formData,
    })
    .then((response) => response.text())
    .then((extractedText) => {
        extractedTextDiv.innerText = extractedText;
    })
    .catch((error) => console.error(error));
});
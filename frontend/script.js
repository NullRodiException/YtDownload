const urlInput = document.getElementById('url-input');
const formatSelect = document.getElementById('format-select');
const downloadBtn = document.getElementById('download-btn');
const videoPlayerContainer = document.getElementById('video-player');
const endpoint = 'http://localhost:8080/download';

function getYouTubeVideoId(url) {
    let videoId = '';
    const urlPatterns = [
        /(?:https?:\/\/)?(?:www\.)?youtube\.com\/watch\?v=([^&]+)/,
        /(?:https?:\/\/)?(?:www\.)?youtu\.be\/([^?]+)/
    ];
    for (const pattern of urlPatterns) {
        const match = url.match(pattern);
        if (match && match[1]) {
            videoId = match[1];
            break;
        }
    }
    return videoId;
}

function addMediaFormats() {
    fetch(endpoint, {
        method: "GET",
        headers: {
            'Content-Type': 'application/json',
        },
    }).then(response => {
        if (!response.ok) {
            throw new Error(`Erro na rede: ${response.statusText}`);
        }
        return response.json();
    }).then(data => {
        for (const format of data.formats) {
            const option = document.createElement('option');
            option.value = format;
            option.innerText = format;
            formatSelect.appendChild(option);
        }
    }).catch(error => {
        console.error('Erro buscar formatos: ', error);
    })
}

urlInput.addEventListener('input', () => {
    const url = urlInput.value;
    const videoId = getYouTubeVideoId(url);

    if (videoId) {
        videoPlayerContainer.innerHTML = `
            <iframe 
                src="https://www.youtube.com/embed/${videoId}" 
                frameborder="0" 
                allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" 
                allowfullscreen>
            </iframe>`;
    } else {
        videoPlayerContainer.innerHTML = '<div class="video-placeholder">Video</div>';
    }
});

downloadBtn.addEventListener('click', () => {
    const url = urlInput.value;
    console.log('URL do vídeo:', url);
    const format = formatSelect.value;

    if (!url) {
        alert('Por favor, insira uma URL!');
        return;
    }

    const data = {url, format };

    console.log('Enviando requisição para:', endpoint);
    console.log('Com os dados:', data);

    fetch(endpoint, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(data),
    })
        .then(response => {
            if (!response.ok) {
                throw new Error(`Erro na rede: ${response.statusText}`);
            }
            return response.blob();
        })
        .then(blob => {
            const downloadUrl = window.URL.createObjectURL(blob);
            console.log(downloadUrl)
            const a = document.createElement('a');
            a.style.display = 'none';
            a.href = downloadUrl;
            a.download = `video.${format}`;
            document.body.appendChild(a);
            a.click();
            window.URL.revokeObjectURL(downloadUrl);
            alert('Download iniciado!');
        })
        .catch(error => {
            console.error('Erro ao tentar fazer o download:', error);
            alert('Falha no download. Verifique se seu servidor local (backend) está rodando na porta 8080 e verifique o console para mais detalhes.');
        });
});

addMediaFormats();





from flask import Flask, send_file
import os

app = Flask(__name__)

# Endpoint para listar todos os vídeos na pasta raiz
@app.route('/videos')
def listar_videos():
    videos = [f for f in os.listdir('.') if os.path.isfile(f) and f.endswith('.mp4')]
    return {'videos': videos}

# Endpoint para obter um vídeo específico pelo nome do arquivo
@app.route('/videos/<video_name>')
def obter_video(video_name):
    if os.path.isfile(video_name) and video_name.endswith('.mp4'):
        return send_file(video_name, as_attachment=True)
    else:
        return {'error': 'Vídeo não encontrado'}, 404

if __name__ == '__main__':
    app.run(debug=True)

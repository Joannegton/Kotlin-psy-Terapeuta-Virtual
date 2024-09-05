import cv2
import pywhatkit

# Função para recortar os últimos 30 segundos de um vídeo
def recortar_ultimos_30_segundos(video_path):
    cap = cv2.VideoCapture(video_path)
    fps = cap.get(cv2.CAP_PROP_FPS)
    total_frames = int(cap.get(cv2.CAP_PROP_FRAME_COUNT))
    duration = total_frames / fps

    # Define o ponto de início para capturar os últimos 30 segundos
    start_frame = max(total_frames - 30 * fps, 0)

    # Define o ponto de finalização
    end_frame = total_frames

    cap.set(cv2.CAP_PROP_POS_FRAMES, start_frame)

    # Inicializa o recorte
    out = cv2.VideoWriter('ultimos_30_segundos.mp4', cv2.VideoWriter_fourcc(*'mp4v'), fps, (int(cap.get(cv2.CAP_PROP_FRAME_WIDTH)), int(cap.get(cv2.CAP_PROP_FRAME_HEIGHT))))

    while cap.isOpened():
        ret, frame = cap.read()
        if not ret:
            break
        out.write(frame)
        current_frame = cap.get(cv2.CAP_PROP_POS_FRAMES)
        # Para o recorte no ponto de finalização
        if current_frame >= end_frame:
            break

    cap.release()
    out.release()

def enviar_whats():
     pywhatkit.sendwhatmsg(phone_no="+5511970179936", message=f"https://9c1a-45-172-198-52.ngrok-free.app/videos/ultimos_30_segundos.mp4", time_hour=15, time_min=1)

if __name__ == "__main__":
   # recortar_ultimos_30_segundos("video.mp4")
    enviar_whats()

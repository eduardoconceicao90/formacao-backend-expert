package io.github.eduardoconceicao90.email_service.dto;

public record EmailRequest(
        String listaDestinatarios,
        String nomeRemetente,
        String assuntoEmail,
        String templatePath
) {
}

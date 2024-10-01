package ar.edu.unq.epers.unidad4.exception;

import ar.edu.unq.epers.unidad4.model.Gremio;

final public class GremioSinEspacioException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private final Gremio gremio;

    public GremioSinEspacioException(Gremio gremio) {
        this.gremio = gremio;
    }

    @Override
    public String getMessage() {
        return "El gremio [" + gremio.getNombre() + "] no tiene espacio para nuevos integrantes";
    }

}

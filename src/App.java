import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {

    public static final int TIPO_DOCUMENTO_IDENTIFICACION = 0;
    public static final int NUMERO_DOCUMENTO_IDENTIFICACION = 1;
    public static final int NOMBRES = 2;
    public static final int DIRECCION = 3;
    public static final int CIUDAD = 4;
    public static final int TELEFONO = 5;
    public static final int CONTRASENA = 6; // Nueva constante para la contraseña

    public static List<String[]> usuarios = new ArrayList<>();
    public static Scanner leerDatoTeclado = new Scanner(System.in);

    public static void mostrarMenuLoginRegistro() {
        System.out.println("Bienvenido a MyHotel ...");
        System.out.println("Mas que un lugar para descansar.");
        System.out.println("----------------------------------------------");
        System.out.println("Ingrese la opción deseada:");
        System.out.println("1. Registrarse como cliente.");
        System.out.println("2. Iniciar Sesión.");
        System.out.println("3. Mostrar datos de los usuarios registrados.");
        System.out.println("4. Salir");

        int opcion = leerDatoTeclado.nextInt();

        switch (opcion) {
            case 1:
                solicitarDatosDeRegistro();
                break;
            case 2:
                iniciarSesion();
                break;
            case 3:
                if (usuarios.isEmpty()) {
                    System.out.println("No hay datos almacenados.");
                    mostrarMenuLoginRegistro(); // Reinicia el menú
                } else {
                    mostrarUsuariosRegistrados();
                }
                break;
            case 4:
                System.out.println("¡Hasta pronto! ");
                mostrarMenuLoginRegistro(); // Reinicia el menú
                break;
            default:
                System.out.println("Opción no válida.");
                mostrarMenuLoginRegistro(); // Reinicia el menú
        }
    }

    public static void solicitarDatosDeRegistro() {
        System.out.println("Ingrese el tipo de identificación:");
        System.out.println("1. Tarjeta de identidad");
        System.out.println("2. Cédula de ciudadanía");
        System.out.println("3. Cédula de extranjería");
        System.out.println("Coloque un número según su documento:");
    
        String tipoIdentificacion = "";
        String numeroIdentificacion = "";
    
        int tipo = leerDatoTeclado.nextInt();
        switch (tipo) {
            case 1:
                tipoIdentificacion = "Tarjeta de identidad";
                System.out.println("Ingresa tu número de tarjeta de identidad:");
                numeroIdentificacion = leerDatoTeclado.next();
                break;
            case 2:
                tipoIdentificacion = "Cédula de ciudadanía";
                System.out.println("Ingresa tu número de cédula:");
                numeroIdentificacion = leerDatoTeclado.next();
                break;
            case 3:
                tipoIdentificacion = "Cédula de extranjería";
                System.out.println("Ingresa tu número de cédula de extranjería:");
                numeroIdentificacion = leerDatoTeclado.next();
                break;
            default:
                System.out.println("Opción no válida.");
                return;
        }
    
        // Consumir el salto de línea pendiente
        leerDatoTeclado.nextLine();
    
        registrarUsuario(tipoIdentificacion, numeroIdentificacion);
    }

    public static void registrarUsuario(String tipoDocumentoIdentificacion, String numeroDocumentoIdentificacion) {
        // Si el usuario ya existe, pedir que ingrese una contraseña
        if (buscarUsuarioPorNumeroDocumento(numeroDocumentoIdentificacion) != null) {
            System.out.println("Ya existe un usuario con ese número de documento. Por favor, ingrese una contraseña:");
            String contrasena;
            String confirmacionContrasena;
            boolean contrasenasCoinciden = false;
    
            do {
                System.out.println("Ingrese la contraseña:");
                contrasena = leerDatoTeclado.nextLine();
    
                if (contrasena.isEmpty()) {
                    System.out.println("La contraseña no puede estar vacía. Intente nuevamente.");
                    continue;
                }
    
                System.out.println("Confirme la contraseña:");
                confirmacionContrasena = leerDatoTeclado.nextLine();
    
                if (!confirmacionContrasena.equals(contrasena)) {
                    System.out.println("Las contraseñas no coinciden. Intente nuevamente.");
                    contrasenasCoinciden = false;
                } else {
                    contrasenasCoinciden = true;
                }
            } while (!contrasenasCoinciden);
    
            // Si las contraseñas coinciden, establecer la contraseña en el usuario existente
            String[] usuarioExistente = buscarUsuarioPorNumeroDocumento(numeroDocumentoIdentificacion);
            usuarioExistente[CONTRASENA] = contrasena;
            System.out.println("Contraseña establecida correctamente.");
            return;
        }
    
        // Si el usuario no existe, proceder con el registro completo
        System.out.println("Pon tu nombre completo:");
        String nombreCompleto = leerDatoTeclado.nextLine(); // Solicitar el nombre completo
    
        // Solicitar la dirección de residencia
        System.out.println("Ingrese la dirección de residencia:");
        String direccion = leerDatoTeclado.nextLine();
    
        // Solicitar la ciudad de residencia
        System.out.println("Ingrese la ciudad de residencia:");
        String ciudad = leerDatoTeclado.nextLine();
    
        // Solicitar el teléfono de contacto
        String telefono;
        do {
            System.out.println("Ingrese el teléfono de contacto:");
            telefono = leerDatoTeclado.nextLine();
            if (!telefono.matches("\\d+")) {
                System.out.println("El teléfono debe contener solo números. Intente nuevamente.");
            }
        } while (!telefono.matches("\\d+"));
    
        // Solicitar la contraseña
        String contrasena;
        boolean contrasenaValida;
        do {
            System.out.println("Ingrese la contraseña:");
            contrasena = leerDatoTeclado.nextLine();
            if (contrasena.isEmpty()) {
                System.out.println("La contraseña no puede estar vacía. Intente nuevamente.");
                contrasenaValida = false;
            } else {
                contrasenaValida = true;
            }
        } while (!contrasenaValida);
    
        // Confirmar la contraseña
        String confirmacionContrasena;
        do {
            System.out.println("Confirme la contraseña:");
            confirmacionContrasena = leerDatoTeclado.nextLine();
            if (!confirmacionContrasena.equals(contrasena)) {
                System.out.println("Las contraseñas no coinciden. Intente nuevamente.");
            }
        } while (!confirmacionContrasena.equals(contrasena));
    
        // Crear el nuevo usuario y agregarlo a la lista de usuarios
        String[] usuario = new String[7];
        usuario[TIPO_DOCUMENTO_IDENTIFICACION] = tipoDocumentoIdentificacion;
        usuario[NUMERO_DOCUMENTO_IDENTIFICACION] = numeroDocumentoIdentificacion;
        usuario[NOMBRES] = nombreCompleto;
        usuario[DIRECCION] = direccion;
        usuario[CIUDAD] = ciudad;
        usuario[TELEFONO] = telefono;
        usuario[CONTRASENA] = contrasena;
        usuarios.add(usuario);
    
        System.out.println("Usuario registrado correctamente.");
    
        mostrarMenuLoginRegistro(); // Llama al menú solo si el registro se completa con éxito
    }
    public static void iniciarSesion() {
        System.out.println("Ingrese el documento de identificación:");
        String documento = leerDatoTeclado.next();
        System.out.println("Ingrese la contraseña:");
        String contrasena = leerDatoTeclado.next();

        for (int i = 0; i < usuarios.size(); i++) {
            String[] usuario = usuarios.get(i);
            if (documento.equals(usuario[NUMERO_DOCUMENTO_IDENTIFICACION]) && contrasena.equals(usuario[CONTRASENA])) {
                System.out.println("Usuario logueado correctamente.");
                mostrarMenuLoginRegistro(); // Reinicia el menú
                return; // Se encontró un usuario válido, se finaliza la función
            }
        }

        System.out.println("Usuario incorrecto, intente una vez más.");
        mostrarMenuLoginRegistro(); // Si ningún usuario coincide, se muestra el menú de nuevo
    }

    public static void mostrarUsuariosRegistrados() {
        System.out.println("Datos de usuarios registrados:");
        for (String[] usuario : usuarios) {
            String tipoDocumento = usuario[TIPO_DOCUMENTO_IDENTIFICACION];
            String numeroDocumento = usuario[NUMERO_DOCUMENTO_IDENTIFICACION];
            String nombres = usuario[NOMBRES];
            String direccion = usuario[DIRECCION];
            String ciudad = usuario[CIUDAD];
            String telefono = usuario[TELEFONO];
            String contrasena = usuario[CONTRASENA];
            
            System.out.println("Tipo de documento: " + tipoDocumento);
            System.out.println("Número de documento: " + numeroDocumento);
            System.out.println("Nombres: " + nombres);
            System.out.println("Dirección: " + direccion);
            System.out.println("Ciudad: " + ciudad);
            System.out.println("Teléfono: " + telefono);
            System.out.println("Contraseña: " + contrasena);
            System.out.println("--------------------------");
        }
        mostrarMenuLoginRegistro();
    }
    
    public static String[] buscarUsuarioPorNumeroDocumento(String numeroDocumento) {
        for (String[] usuario : usuarios) {
            if (numeroDocumento.equals(usuario[NUMERO_DOCUMENTO_IDENTIFICACION])) {
                return usuario;
            }
        }
        return null; // Si no se encuentra ningún usuario con el número de documento especificado
    }
    
    public static void main(String[] args) {
        mostrarMenuLoginRegistro();
    }
}
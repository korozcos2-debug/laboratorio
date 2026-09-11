package Main;

import datos.ProductoDao;
import modelo.Producto;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ProductoDao dao = new ProductoDao();
        int opcion = 0;

        do {
            System.out.println("\n--- MENÚ GESTIÓN DE PRODUCTOS ---");
            System.out.println("1. Crear producto");
            System.out.println("2. Listar productos");
            System.out.println("3. Buscar producto por ID");
            System.out.println("4. Actualizar producto");
            System.out.println("5. Eliminar producto");
            System.out.println("6. Salir");
            System.out.print("Elija una opción: ");

            if (scanner.hasNextInt()) {
                opcion = scanner.nextInt();
                scanner.nextLine(); // Limpiar buffer

                switch (opcion) {
                    case 1:
                        System.out.print("Nombre: ");
                        String nombre = scanner.nextLine();
                        System.out.print("Categoría: ");
                        String cat = scanner.nextLine();
                        System.out.print("Precio: ");
                        double precio = scanner.nextDouble();
                        System.out.print("Stock: ");
                        int stock = scanner.nextInt();

                        Producto nuevo = new Producto(nombre, cat, precio, stock);
                        if (dao.crear(nuevo)) {
                            System.out.println("¡Producto creado con éxito!");
                        }
                        break;

                    case 2:
                        System.out.println("\n--- LISTA DE PRODUCTOS ---");
                        dao.listar().forEach(System.out::println);
                        break;

                    case 3:
                        System.out.print("Ingrese ID a buscar: ");
                        int idBuscar = scanner.nextInt();
                        dao.buscarPorId(idBuscar).ifPresentOrElse(
                            System.out::println,
                            () -> System.out.println("No existe un producto con ese id")
                        );
                        break;

                    case 4:
                        System.out.print("Ingrese ID del producto a actualizar: ");
                        int idAct = scanner.nextInt();
                        scanner.nextLine();
                        
                        if (dao.buscarPorId(idAct).isPresent()) {
                            System.out.print("Nuevo nombre: ");
                            String nNom = scanner.nextLine();
                            System.out.print("Nueva categoría: ");
                            String nCat = scanner.nextLine();
                            System.out.print("Nuevo precio: ");
                            double nPrecio = scanner.nextDouble();
                            System.out.print("Nuevo stock: ");
                            int nStock = scanner.nextInt();

                            Producto actualizado = new Producto(idAct, nNom, nCat, nPrecio, nStock);
                            if (dao.actualizar(actualizado)) {
                                System.out.println("¡Producto actualizado correctamente!");
                            }
                        } else {
                            System.out.println("No existe un producto con ese id");
                        }
                        break;

                    case 5:
                        System.out.print("Ingrese ID del producto a eliminar: ");
                        int idDel = scanner.nextInt();
                        scanner.nextLine();

                        if (dao.buscarPorId(idDel).isPresent()) {
                            System.out.print("¿Está seguro de eliminar este producto? (s/n): ");
                            String confirmacion = scanner.nextLine();
                            if (confirmacion.equalsIgnoreCase("s")) {
                                if (dao.eliminar(idDel)) {
                                    System.out.println("Producto eliminado correctamente.");
                                }
                            } else {
                                System.out.println("Operación cancelada.");
                            }
                        } else {
                            System.out.println("No existe un producto con ese id");
                        }
                        break;

                    case 6:
                        System.out.println("Saliendo del programa...");
                        break;

                    default:
                        System.out.println("Opción no válida.");
                }
            } else {
                System.out.println("Por favor, ingrese un número válido.");
                scanner.next();
            }
        } while (opcion != 6);

        scanner.close();
    }
}
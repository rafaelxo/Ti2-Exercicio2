package ti2_maven;

import java.util.*;

public class Principal {
	public static Scanner sc = new Scanner (System.in);
	public static void main(String[] args) {
		
		// Conexão com DAO
		DAO dao = new DAO();
		dao.conectar();
		
		// Menu de opções CRUD
		int op = 0;
		do {
			System.out.println("\nMENU DE OPÇÕES\n");
            System.out.println("1 - Listar usuários");
            System.out.println("2 - Inserir usuário");
            System.out.println("3 - Atualizar usuário");
            System.out.println("4 - Excluir usuário");
            System.out.println("5 - Sair\n");
            System.out.print("Escolha a opção desejada: ");
            op = sc.nextInt();
			switch (op) {
				case 1: listarUsuarios(dao); break;
				case 2: inserirUsuario(dao); break;
				case 3: atualizarUsuario(dao); break;
				case 4: excluirUsuario(dao); break;
				case 5:
					dao.close();
					System.out.println("Conexão encerrada.");
                break;
				default: System.out.println("Opção inválida!");
			}
		} while (op != 5);
	}
	
	// Mostrar os usuários do sistema
	private static void listarUsuarios(DAO dao) {
	    Usuario[] usuarios = dao.getUsuarios();
	    if (usuarios != null && usuarios.length > 0) {
	        for (Usuario u : usuarios) System.out.println(u.toString());
	    } else System.out.println("Nenhum usuário encontrado.");
	}

		
	// Inserir um novo usuário ao sistema
	private static void inserirUsuario(DAO dao) {
		sc.nextLine();
		System.out.print("Informe o novo login: "); String log = sc.nextLine();
	    System.out.print("Informe a nova senha: "); String sen = sc.nextLine();
	    System.out.print("Informe o novo sexo (M/F): "); char sex = sc.next().charAt(0);
		Usuario[] usuarios = dao.getUsuarios();
	    int cod = (usuarios != null && usuarios.length > 0) ? usuarios[usuarios.length - 1].getCodigo() + 1 : 1;
	    Usuario novo = new Usuario(cod, log, sen, sex);
	    if (dao.inserirUsuario(novo)) System.out.println("Usuário inserido com sucesso!");
	}
			
	// Alterar usuário informado
	private static void atualizarUsuario(DAO dao) {
		System.out.println("Informe o codigo do usuario que deseja alterar: ");
		int cod = sc.nextInt();
		sc.nextLine();
		Usuario[] usuarios = dao.getUsuarios();
		boolean encontrado = false;
		if (usuarios != null) {
			for (Usuario u : usuarios) {
				if (u.getCodigo() == cod) {
					encontrado = true;
					break;
				}
			}
		}
		if (!encontrado) {
			System.out.println("Usuário de código " + cod + " não encontrado!");
			return;
		}
		System.out.print("Informe o novo login: "); String log = sc.nextLine();
	    System.out.print("Informe a nova senha: "); String sen = sc.nextLine();
	    System.out.print("Informe o novo sexo (M/F): "); char sex = sc.next().charAt(0);
	    Usuario alt = new Usuario(cod, log, sen, sex);
	    if (dao.atualizarUsuario(alt)) System.out.println("Usuário atualizado com sucesso!");
	}
			
	// Excluir usuário informado
	private static void excluirUsuario(DAO dao) {
		System.out.print("Informe o codigo do usuario que deseja excluir: ");
		int cod = sc.nextInt();
		sc.nextLine();
		Usuario[] usuarios = dao.getUsuarios();
		boolean encontrado = false;

		if (usuarios != null) {
			for (Usuario u : usuarios) {
				if (u.getCodigo() == cod) {
					encontrado = true;
					break;
				}
			}
		}
		if (!encontrado) {
			System.out.println("❌ Usuário com código " + cod + " não encontrado!");
			return;
		}
		if (dao.excluirUsuario(cod)) System.out.println("Usuário excluído com sucesso!");
	}
}
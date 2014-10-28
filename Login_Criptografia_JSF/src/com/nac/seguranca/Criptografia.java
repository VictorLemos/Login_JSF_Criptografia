package com.nac.seguranca;

import java.math.BigInteger;
import java.security.MessageDigest;

public class Criptografia {

	public static String encriptar(String mensagem) {

		String senhaCriptografada = null;

		try {
			// Obtém a instância de um algoritmo
			MessageDigest md = MessageDigest.getInstance("MD5"); // MD5 ou SHA-1
																	// ou SHA-256
			// Passa os dados a serem criptografados e estipula enconding padrão
			md.update(mensagem.getBytes("ISO-8859-1"));
			// Gera a chave criptografada em array de Bytes para posterior
			// hashing
			BigInteger hash = new BigInteger(1, md.digest());
			senhaCriptografada = hash.toString(16); // Hash em Base16 Encoding
			String zeros = "";
			if (senhaCriptografada.length() < 32) {
				for (int i = 0; i < (32 - senhaCriptografada.length()); i++) {
					zeros += "0";
				}
			}
			senhaCriptografada = zeros + senhaCriptografada;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return senhaCriptografada;
	}

}

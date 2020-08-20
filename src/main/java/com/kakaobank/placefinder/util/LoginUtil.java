package com.kakaobank.placefinder.util;

import com.kakaobank.placefinder.constant.StatusCode;
import com.kakaobank.placefinder.exception.PlaceFinderException;
import com.nimbusds.jose.Algorithm;
import com.nimbusds.jose.JOSEObjectType;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.JWTParser;
import com.nimbusds.jwt.SignedJWT;

import java.security.MessageDigest;
import java.util.Date;

public class LoginUtil {

    private static final String PASSWORD_ALGORITHM = "SHA-256";
    private static final String JWT_ISSUER = "PLACE_FINDER_LOGIN";
    private static final String JWT_SECRET = "TB*wGgBjkfVY(fEP";
    private static final long LOGIN_VALID_TIME = 600000;

    public static String passwordEncode(String password) throws PlaceFinderException {

        try {
            return byteArrayToHex(hash256(password));
        } catch (Exception e) {
            throw new PlaceFinderException(StatusCode.E1500);
        }
    }

    public static byte[] hash256(String password) throws PlaceFinderException {

        MessageDigest md;

        try {
            md = MessageDigest.getInstance(PASSWORD_ALGORITHM);
            return md.digest(password.getBytes());
        } catch (Exception e) {
            throw new PlaceFinderException(StatusCode.E1500);
        }
    }

    public static String byteArrayToHex(byte[] binary) {

        if (null == binary || 0 >= binary.length) {
            return "";
        }

        StringBuffer sb = new StringBuffer(binary.length * 2);
        String hexNumber = null;

        for (int i = 0; i < binary.length; i++) {
            hexNumber = "0" + Integer.toHexString(0xff & binary[i]);
            sb.append(hexNumber.substring(hexNumber.length() - 2));
        }
        return sb.toString();
    }

    public static String createJWT(String loginId) throws PlaceFinderException {

        try {
            long millis = System.currentTimeMillis();
            String sub = loginId;
            JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.HS256)
                    .type(JOSEObjectType.JWT).build();

            JWTClaimsSet idClaims = new JWTClaimsSet.Builder()
                    .subject(sub)
                    .issuer(JWT_ISSUER)
                    .expirationTime(new Date(millis + LOGIN_VALID_TIME)).build();

            SignedJWT signed = new SignedJWT(header, idClaims);
            signed.sign(new MACSigner(hash256(JWT_SECRET)));
            return signed.serialize();

        } catch (Exception e) {
            throw new PlaceFinderException(StatusCode.E1502, e);
        }
    }

    public static String getLoginId(String reqJwt) throws PlaceFinderException {

        if (reqJwt == null) {
            throw new PlaceFinderException(StatusCode.E1412);
        }

        try {

            JWT jwt = JWTParser.parse(reqJwt);
            Algorithm tokenAlg = jwt.getHeader().getAlgorithm();

            if (tokenAlg == null || !tokenAlg.equals(JWSAlgorithm.HS256)) {
                throw new PlaceFinderException(StatusCode.E1411);
            }

            SignedJWT inputSignedJWT = new SignedJWT(
                    jwt.getParsedParts()[0],
                    jwt.getParsedParts()[1],
                    jwt.getParsedParts()[2]);

            if (!inputSignedJWT.verify(new MACVerifier(hash256(JWT_SECRET)))) {
                throw new PlaceFinderException(StatusCode.E1411);
            }

            JWTClaimsSet jwtClaims = jwt.getJWTClaimsSet();

            if (!JWT_ISSUER.equals(jwtClaims.getIssuer())) {
                throw new PlaceFinderException(StatusCode.E1411);
            }

            Date now = new Date(System.currentTimeMillis());
            if (jwtClaims.getExpirationTime() == null || now.after(jwtClaims.getExpirationTime())) {
                throw new PlaceFinderException(StatusCode.E1410);
            }

            return jwtClaims.getSubject();
        } catch (PlaceFinderException e) {
            throw e;
        } catch (Exception e) {
            throw new PlaceFinderException(StatusCode.E1503, e);
        }
    }
}

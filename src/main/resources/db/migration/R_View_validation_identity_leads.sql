CREATE OR REPLACE VIEW View_Validation_identity_leads AS 
select "email validado" as step, count(1) as count ,"email validado" as description FROM people_hub_int.sign_up_request where onboarding_state = "EMAIL_VALIDATED"
UNION
select "email validado agrupado x disp", count(distinct device_id),"sin frente foto dni"  FROM people_hub_int.sign_up_request where onboarding_state = "EMAIL_VALIDATED"
UNION
select "sin frente dni", count(1), "sin foto del frente dni" FROM people_hub_int.sign_up_request where id_front_url is null
and onboarding_state = "EMAIL_VALIDATED"
UNION
select "sin dorso dni", count(1), "sin foto del dorso del dni" FROM people_hub_int.sign_up_request where id_back_url is null
and onboarding_state = "EMAIL_VALIDATED"
UNION
select "sin selfie ", count(1), "sin selfie" FROM people_hub_int.sign_up_request where face_url is null
and onboarding_state = "EMAIL_VALIDATED"
UNION
select "dni_no_valido", count(1), "dni expirado" FROM people_hub_int.sign_up_request where expired_dni = 1
and onboarding_state = "EMAIL_VALIDATED"
UNION
select "info_no_valida", count(1), "info no valida" FROM people_hub_int.sign_up_request where invalid_crosscheck_data = 1
and onboarding_state = "EMAIL_VALIDATED"
UNION
select "aviso fallecimiento", count(1), "aviso fallecimiento" FROM people_hub_int.sign_up_request where demise_notice = 1
and onboarding_state = "EMAIL_VALIDATED"
UNION
select "Sexo NO valido", count(1), "sexo no valido" FROM people_hub_int.sign_up_request where invalid_dni_gender = 1
and onboarding_state = "EMAIL_VALIDATED"
UNION
SELECT "facephi no positivo", count(1), "fallo reconocimiento facephi" FROM people_hub_int.sign_up_request
where JSON_EXTRACT(facephi_verify_face_match,"$.status") != 'Positive'
and onboarding_state = "EMAIL_VALIDATED"
UNION
SELECT "facephi bloqueado", count(1), "facephi bloqueo al usuario" FROM people_hub_int.sign_up_request
where facephi_wrong_face_match_count = 5
and onboarding_state = "EMAIL_VALIDATED"
UNION
SELECT "renaper NO positivo", count(1), "no coincide con la foto de la base de datos renaper" FROM people_hub_int.sign_up_request
where JSON_EXTRACT(renaper_verify_photo,"$.identical") != true
and onboarding_state = "EMAIL_VALIDATED"
UNION
SELECT "renaper bloqueado", count(1), "renaper bloqueo al usuario" FROM people_hub_int.sign_up_request
where renaper_wrong_face_match_count = 2
and onboarding_state = "EMAIL_VALIDATED"
UNION
SELECT "renaper technical error", count(1), "error tecnico con renaper" FROM people_hub_int.sign_up_request
where JSON_EXTRACT(renaper_verify_photo,"$.code") > 0
and onboarding_state = "EMAIL_VALIDATED";

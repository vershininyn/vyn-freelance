CREATE SCHEMA public;

CREATE TABLE "public.User" (
	"id" serial(128) NOT NULL,
	"login" TEXT(1024) NOT NULL,
	"email" VARCHAR(1024) NOT NULL,
	"password" VARCHAR(2048) NOT NULL,
	"registrationDateAndTime" TIMESTAMP NOT NULL,
	CONSTRAINT "User_pk" PRIMARY KEY ("id")
) WITH (
  OIDS=FALSE
);

CREATE TABLE "public.Role" (
	"id" serial(128) NOT NULL,
	"name" VARCHAR(1024) NOT NULL,
	CONSTRAINT "Role_pk" PRIMARY KEY ("id")
) WITH (
  OIDS=FALSE
);

CREATE TABLE "public.User_Role" (
	"id" serial(128) NOT NULL,
	"user_id" integer(128) NOT NULL,
	"role_id" BINARY(128) NOT NULL,
	CONSTRAINT "User_Role_pk" PRIMARY KEY ("id")
) WITH (
  OIDS=FALSE
);

ALTER TABLE "User_Role" ADD CONSTRAINT "User_Role_fk0" FOREIGN KEY ("user_id") REFERENCES "User"("id");
ALTER TABLE "User_Role" ADD CONSTRAINT "User_Role_fk1" FOREIGN KEY ("role_id") REFERENCES "Role"("id");

INSERT INTO "public.Role"("id","name") VALUES(1, "USER");
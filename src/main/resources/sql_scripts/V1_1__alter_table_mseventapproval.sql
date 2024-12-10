DROP TABLE IF EXISTS "public"."ms_eventapproval";
CREATE TABLE "public"."ms_eventapproval" (
  "id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "created_by" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "created_date" timestamp(6) NOT NULL,
  "modified_by" varchar(255) COLLATE "pg_catalog"."default",
  "modified_date" timestamp(6),
  "code" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "deleted_reason" varchar(255) COLLATE "pg_catalog"."default",
  "is_active" bool NOT NULL,
  "is_deleted" bool NOT NULL,
  "name" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "remarks" varchar(255) COLLATE "pg_catalog"."default",
  "source_program" varchar(255) COLLATE "pg_catalog"."default" NOT NULL
)
;
ALTER TABLE "public"."ms_eventapproval" OWNER TO "postgres";

-- ----------------------------
-- Primary Key structure for table ms_eventapproval
-- ----------------------------
ALTER TABLE "public"."ms_eventapproval" ADD CONSTRAINT "ms_eventapproval_pkey" PRIMARY KEY ("id");
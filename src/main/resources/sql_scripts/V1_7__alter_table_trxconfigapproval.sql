DROP TABLE IF EXISTS "public"."trx_configapproval";
CREATE TABLE "public"."trx_configapproval" (
  "id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "created_by" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "created_date" timestamp(6) NOT NULL,
  "modified_by" varchar(255) COLLATE "pg_catalog"."default",
  "modified_date" timestamp(6),
  "auto_send_request" bool NOT NULL,
  "branch" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "deleted_reason" varchar(255) COLLATE "pg_catalog"."default",
  "effective_date" timestamp(6) NOT NULL,
  "endpoint_on_finished" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "frontend_view" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "is_deleted" bool NOT NULL,
  "ms_eventapproval_code" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "ms_eventapproval_id" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "remarks" varchar(255) COLLATE "pg_catalog"."default",
  "stop_when_rejected" bool NOT NULL
)
;
ALTER TABLE "public"."trx_configapproval" OWNER TO "postgres";

-- ----------------------------
-- Primary Key structure for table trx_configapproval
-- ----------------------------
ALTER TABLE "public"."trx_configapproval" ADD CONSTRAINT "trx_configapproval_pkey" PRIMARY KEY ("id");

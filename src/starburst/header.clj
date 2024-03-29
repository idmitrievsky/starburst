(ns starburst.header)

(defn comment-block
  [lines]
  (let [^StringBuilder builder (StringBuilder.)]
    (doseq [line lines]
      (.append builder (str ";;; " line))
      (.append builder "\n"))
    (str builder)))

(defn add-warning
  [file-struct]
  (assoc file-struct
    :warning
    (symbol (comment-block ["This file was auto-generated by starburst.",
                            "Do not modify it by hand."]))))

(defn add-dependency
  [file-struct name]
  (update-in file-struct [:dependencies] conj (->> name symbol (conj []))))

(defn add-common-dependencies
  [file-struct]
  (-> file-struct
      (add-dependency "clojure.spec")
      (add-dependency "clojure.spec.test")))

(defn add-orion-dependency
  [file-struct name]
  (add-dependency file-struct (str "orion." name)))

(defn create-namespace
  [name]
  (-> {:name         (str "orion." name)
       :dependencies []
       :definitions  []}
      (add-warning)
      (add-common-dependencies)))


